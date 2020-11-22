package avro.test;

import com.lcgblog.Gender;
import com.lcgblog.User;
import java.io.File;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

public class SerializationTest {

  public static void main(String[] args) throws Exception{
    read();
  }

  private static void read()throws Exception{
    DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
    DataFileReader<User> dataFileReader = new DataFileReader<>(new File("users.avro"), userDatumReader);
    User user = dataFileReader.next();
    System.out.println(user);
  }

  private static void write()throws Exception{
    User user = User.newBuilder().setName("123").setFavoriteNumber(1).setFavoriteColor("Red").setGender(
        Gender.MALE).build();

//    DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
//    DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter);
//    dataFileWriter.create(User.getClassSchema(), new File("users.avro"));
//    dataFileWriter.append(user);
//    dataFileWriter.close();
  }
}
