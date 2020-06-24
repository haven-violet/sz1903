import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author liaojincheng
 * @Date 2020/6/10 11:40
 * @Version 1.0
 * @Description
 */
public class ProducerTest {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(ProducerTest.class.getClassLoader().getResourceAsStream("producer.properties"));
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
        String topic = "spark";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\ljc\\Desktop\\order.log")));
        String temp = null;

        while((temp = br.readLine()) != null){
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, temp);
            producer.send(record);
        }
        producer.close();
    }
}
