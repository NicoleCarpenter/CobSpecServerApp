package carpentern.cobSpecApp.file;

import java.io.File;

public interface FileIO {
  byte[] getFileContents(String file);
  byte[] getPartialFileContents(String filePath, String range);
  void writeToFile(String filePath, String content);
  void updateFile(String filePath, String content);
  void deleteFileContent(String fileName);
  File getRootDirectory();
}