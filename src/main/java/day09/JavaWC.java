package day09;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Author liaojincheng
 * @Date 2020/5/13 23:19
 * @Version 1.0
 * @Description
 */
public class JavaWC {
    public static void main(String[] args) {
        //创建执行入口
        SparkConf conf = new SparkConf().setAppName("javaWC").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        //读取数据文件
        JavaRDD<String> lines = jsc.textFile("F:\\test.txt");
        //处理数据
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                List<String> list = Arrays.asList(s.split(" "));
                return list.iterator();
            }
        });
        //将数据变成Tuple元组
        JavaPairRDD<String, Integer> tuples = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        });
        //聚合操作
        JavaPairRDD<String, Integer> result = tuples.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        //排序之前需要将数据处理一下,因为在JavaAPI中没有sortBy这个算子
        JavaPairRDD<Integer, String> swapRes = result.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> t) throws Exception {
                //将元组内的数据翻转
                //return t.swap();
                return new Tuple2<>(t._2, t._1);
            }
        });
        //排序操作(排序)
        JavaPairRDD<Integer, String> sortRes = swapRes.sortByKey(false);
        sortRes.foreach(new VoidFunction<Tuple2<Integer, String>>() {
            @Override
            public void call(Tuple2<Integer, String> t) throws Exception {
                System.out.println(t._2()+" "+ t._1());
            }
        });
    }
}
