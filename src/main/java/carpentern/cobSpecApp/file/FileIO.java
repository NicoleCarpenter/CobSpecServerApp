package carpentern.cobSpecApp.file;

import carpentern.coreServer.request.HttpRequest;
import java.io.File;

public interface FileIO {
  public abstract byte[] getFileContents(String file);
  public abstract byte[] getPartialFileContents(String filePath, String range);
  public abstract void writeToFile(String filePath, String content);
  public abstract void updateFile(String filePath, String content);
  public abstract void deleteFileContent(String fileName);
  public abstract File getRootDirectory();
}