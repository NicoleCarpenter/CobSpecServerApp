package carpentern.cobSpecApp.file;

import java.io.File;

public interface FileSystem {
  boolean exists(String file);
  boolean isFile(String file);
  String[] list(String directory);
  String getFileAbsolutePath(File file);
  String getFileName(File file);
}