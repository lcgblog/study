package avro.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerDemo {

  public static final String brokerList = "kafka-node01:9092";
  public static final String topic = "test";
  public static final String groupId = "group.demo";


  public static Properties initConfig(){
    Properties properties = new Properties();
    properties.put("bootstrap.servers", brokerList);
    properties.put("key.deserializer", StringDeserializer.class.getName());
    properties.put("value.deserializer", StringDeserializer.class.getName());
    properties.put("client.id","consumer.demo");
    properties.put("group.id",groupId);
    return properties;
  }

  public static void main(String[] args) {
    Properties props = initConfig();
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Arrays.asList(topic));
    while(true){
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
      for(ConsumerRecord<String, String> record : records){
        System.out.println("topic = " + record.topic() + ", partition="+ record.partition() + ", offset=" + record.offset());
        System.out.println("key="+record.key() +", value="+record.value());
      }
    }
  }
}
