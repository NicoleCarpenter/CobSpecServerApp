package carpentern.cobSpecApp.file;

import java.io.File;

public interface FileSystem {
  public boolean exists(String file);
  public boolean isFile(String file);
  public String[] list(String directory);
  public String getName(String file);
  public String getAbsolutePath(String file);
  public String getFileAbsolutePath(File file);
  public String getFileName(File file);
}