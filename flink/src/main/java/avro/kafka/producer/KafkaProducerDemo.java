package avro.kafka.producer;

import java.util.Properties;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerDemo {

  public static final String brokerList = "kafka-node01:9092";
  public static final String topic = "test";

  public static Properties initConfig(){
    Properties properties = new Properties();
    properties.put("bootstrap.servers", brokerList);
    properties.put("key.serializer", StringSerializer.class.getName());
    properties.put("value.serializer", StringSerializer.class.getName());
    properties.put("client.id","producer.demo");
    return properties;
  }

  public static void main(String[] args) throws Exception{
   Properties props = initConfig();
   KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);
   ProducerRecord<String, String> record = new ProducerRecord<>(topic ,"Hello, Kafka!");
    Future<RecordMetadata> send = kafkaProducer.send(record);
    RecordMetadata recordMetadata = send.get();
    System.out.println(recordMetadata);

  }
}
