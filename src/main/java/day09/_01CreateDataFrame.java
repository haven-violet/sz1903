package day09;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;

import java.util.Arrays;
import java.util.List;

/**
 * @Author liaojincheng
 * @Date 2020/5/23 10:18
 * @Version 1.0
 * @Description
 */
public class _01CreateDataFrame {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName(_01CreateDataFrame.class.getName()).master("local").getOrCreate();
        RDD<String> lines = spark.sparkContext().textFile("F:\\教学视频\\千峰大数据\\电商数仓\\day29-SparkSQL\\资料\\students.txt", 2);
        /**
         * 方式1: 反射创建DataFrame
         */
        JavaRDD<Student> words = lines.toJavaRDD().map(new Function<String, Student>() {
            @Override
            public Student call(String v1) throws Exception {
                String[] str = v1.split(",");
                Student student = new Student();
                student.setId(Integer.parseInt(str[0]));
                student.setName(str[1]);
                student.setAge(Integer.parseInt(str[2]));
                return student;
            }
        });
        Dataset<Row> dataFrame = spark.createDataFrame(words, Student.class);
        dataFrame.createOrReplaceTempView("stu");
        String sql = "select * from stu where age < 19";
        spark.sql(sql).show();

        /**
         * 方式2: 动态编程
         */
        JavaRDD<Row> words1 = lines.toJavaRDD().map(new Function<String, Row>() {
            @Override
            public Row call(String v1) throws Exception {
                String[] str = v1.split(",");

                return RowFactory.create(Integer.parseInt(str[0]), str[1], Integer.parseInt(str[2]));
            }
        });

        List<StructField> list = Arrays.asList(
                DataTypes.createStructField("id", DataTypes.IntegerType, true),
                DataTypes.createStructField("name", DataTypes.StringType, true),
                DataTypes.createStructField("age", DataTypes.IntegerType, true)
        );
        Dataset<Row> dataFrame1 = spark.createDataFrame(words1, DataTypes.createStructType(list));
        dataFrame1.createOrReplaceTempView("stu1");
        String sql1 = "select * from stu1 where age >= 18";
        spark.sql(sql1).show();
    }
}
