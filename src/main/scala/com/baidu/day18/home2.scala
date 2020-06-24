package com.baidu.day18

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DataTypes, StructField, StructType}

/**
  * @Author liaojincheng
  * @Date 2020/5/27 0:44
  * @Version 1.0
  * @Description
  */
object home2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    //TODO UDF 和 UDAF
    // 注册udf函数 和 udaf 函数
    spark.udf.register("myYear", myDate _)
    spark.udf.register("mySum", new MySum)

    spark.sql(
      s"""
         |select
         | myYear(time) as year,
         | mySum(profit) as sumProfit
         |from spark.sale_log
         |group by year
       """.stripMargin).show()

    def myDate(str:String) = {
      str.split("-")(0)
    }
  }
}

class MySum extends UserDefinedAggregateFunction {
  //1.输入参数类型
  override def inputSchema: StructType = {
    StructType(
      Array(
        StructField("profit", DataTypes.DoubleType, false)
      )
    )
  }

  //2.缓冲区类型
  override def bufferSchema: StructType = {
    StructType(
      Array(
        StructField("sum", DataTypes.DoubleType, false)
      )
    )
  }

  //3.返回类型
  override def dataType: DataType = DataTypes.DoubleType

  //4.判断输入类型是否一致
  override def deterministic: Boolean = true

  //5.初始值
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer.update(0, 0.0)
  }

  //6.分区内, 业务逻辑
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer.update(0, buffer.getDouble(0) + input.getDouble(0))
  }

  //7.分区间聚合
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1.update(0, buffer1.getDouble(0) + buffer2.getDouble(0))
  }

  //8.返回值
  override def evaluate(buffer: Row): Any = {
    buffer.getDouble(0)
  }
}