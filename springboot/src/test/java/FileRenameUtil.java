import java.io.File;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

public class FileRenameUtil {

  public void rename()throws Exception{
    String directory = "Y:\\学习\\VIP算法\\";
//    String directory = "D:\\Develop\\projects\\Github\\study\\springboot";
    Collection<File> results = FileUtils
        .listFilesAndDirs(new File(directory), FileFilterUtils.fileFileFilter(),
            FileFilterUtils.directoryFileFilter());
    long resultSize = results.size();
    System.out.println("找到"+results.size()+"个结果");
    int i = 0;
    for (File file : results) {
      String oldName = file.getName();
      if (oldName.matches(".*【.*com.*】.*")) {
        String basePath = file.getParentFile().getAbsolutePath();
        String newName = oldName.replaceAll("【.*】", "");
        String newPath = basePath + "\\" + newName;
        if (file.isDirectory()) {
          FileUtils.moveDirectory(file, new File(newPath));
        } else {
          FileUtils.moveFile(file, new File(newPath));
        }
        System.out.println(basePath + " : " + oldName + "-> "+ newName +". 已处理"+i+"/"+resultSize+"个文件");
      }
      i++;
    }
  }

}
