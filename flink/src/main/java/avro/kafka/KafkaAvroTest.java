package avro.kafka;

import com.lcgblog.User;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;


public class KafkaAvroTest {

  private static final String topic = "avro.test2";

  public static void main(String[] args) {
    write();
//    read();
  }

  public static void read(){
    Properties props = new Properties();

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-node01:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "group18");


    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
    props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
    props.put("schema.registry.url", "http://kafka-node01:8081");

    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    final Consumer<String, User> consumer = new KafkaConsumer<String, User>(props);
    consumer.subscribe(Arrays.asList(topic));

    try {
      while (true) {
        ConsumerRecords<String, User> records = consumer.poll(100);
        for (ConsumerRecord<String, User> record : records) {

          System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
//          System.out.println(record.value().getGender());
        }
      }
    } finally {
      consumer.close();
    }
  }

  public static void write(){
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-node01:9092");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        io.confluent.kafka.serializers.KafkaAvroSerializer.class);
    props.put("schema.registry.url", "http://kafka-node01:8081");
    KafkaProducer producer = new KafkaProducer(props);

    User user = User.newBuilder().setName("123").setFavoriteNumber(1).setFavoriteColor("Red").build();

    ProducerRecord<Object, Object> record = new ProducerRecord<>(topic, user.getName(), user);
    try {
      producer.send(record);
    } catch(SerializationException e) {
      // may need to do something with it
    }
// When you're finished producing records, you can flush the producer to ensure it has all been written to Kafka and
// then close the producer to free its resources.
    finally {
      producer.flush();
      producer.close();
    }
  }
}
