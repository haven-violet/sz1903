package com.baidu.day18

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DataTypes, StructField, StructType}

/**
  * @Author liaojincheng
  * @Date 2020/5/26 23:26
  * @Version 1.0
  * @Description
  * 用户自定义udaf函数,需要继承类UserDefinedAggregateFunction
  */
class MyAvgUDAF extends UserDefinedAggregateFunction{
  //自定义函数的输入参数的类型
  override def inputSchema: StructType = {
    StructType(
      Array(
        StructField("score", DataTypes.FloatType, false)
      )
    )
  }
  //记录在执行计算过程中的临时数据,在本例子中就是sum和count
  //因为我们要的计算的是平均值 sum / count
  override def bufferSchema: StructType = {
    StructType(
      Array(
        StructField("sum", DataTypes.FloatType, false),
        StructField("count", DataTypes.IntegerType, false)
      )
    )
  }

  //该udaf返回值的数据类型
  override def dataType: DataType = DataTypes.FloatType

  //判断输入类型是否一致
  override def deterministic: Boolean = true

  //初始值
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer.update(0, 0f) //处理的就是sum的初始值
    buffer.update(1, 0) //处理的就是count的初始值
  }

  /**
    * 分区内的数据计算
    * @param buffer 缓冲区的数据
    * @param input 调用udaf传入进来的数据,本例中指的就是score
    */
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    val score = input.getFloat(0)
    buffer.update(0, buffer.getFloat(0) + score) //sum += score
    buffer.update(1, buffer.getInt(1) + 1) //count += 1
  }

  //全局聚合操作,不同缓冲区之间进行聚合
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    //sum = sum1 + sum2 ..
    buffer1.update(0, buffer1.getFloat(0) + buffer2.getFloat(0))
    //count = count1 + count2 ..
    buffer1.update(1, buffer1.getInt(1) + buffer2.getInt(1))
  }

  //返回值结果
  override def evaluate(buffer: Row): Any = {
    /**
      * buffer1.update(0, buffer1.getFloat(0) + buffer2.getFloat(0)) = sum
      * buffer1.update(1, buffer1.getInt(1) + buffer2.getInt(1)) = count
      * sum / count
      */
    buffer.getFloat(0) / buffer.getInt(1)
  }
}
