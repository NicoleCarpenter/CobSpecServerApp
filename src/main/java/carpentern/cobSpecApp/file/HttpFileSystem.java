package carpentern.cobSpecApp.file;

import java.io.File;

public class HttpFileSystem implements FileSystem {
  private File f;
  private File d;

  @Override
  public boolean exists(String file) {
    f = new File(file);
    return f.exists();
  }

  @Override
  public boolean isFile(String file) {
    f = new File(file);
    return f.isFile();
  }

  @Override
  public String[] list(String directory) {
    d = new File(directory);
    return d.list();
  }

  @Override
  public String getName(String file) {
    f = new File(file);
    return getFileName(f);
  }

  @Override
  public String getAbsolutePath(String file) {
    f = new File(file);
    return getFileAbsolutePath(f);
  }

  @Override
  public String getFileAbsolutePath(File file) {
    return file.getAbsolutePath();
  }

  @Override
  public String getFileName(File file) {
    return file.getName();
  }
}