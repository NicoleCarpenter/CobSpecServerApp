import carpentern.cobSpecApp.file.FileSystem;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MockHttpFileSystem implements FileSystem {
  boolean existsCalled = false;
  boolean isFileCalled = false;
  boolean listCalled = false;
  boolean getNameCalled = false;
  boolean getFileNameCalled = false;
  boolean getAbsolutePathCalled = false;
  boolean getFileAbsolutePathCalled = false;
  boolean stubbedExists;
  boolean stubbedIsFile;
  String[] stubbedList;
  String stubbedGetName;
  String stubbedGetFileName;
  String stubbedGetAbsolutePath;
  String stubbedGetFileAbsolutePath;

  public boolean exists(String file) {
    existsCalled = true;
    return stubbedExists;
  }

  public boolean isFile(String file) {
    isFileCalled = true;
    return stubbedIsFile;
  }

  public String[] list(String directory) {
    listCalled = true;
    return stubbedList;
  }

  public String getName(String file) {
    getNameCalled = true;
    return stubbedGetName;
  }

  public String getAbsolutePath(String file) {
    getAbsolutePathCalled = true;
    return stubbedGetAbsolutePath;
  }

  public String getFileAbsolutePath(File file) {
    getFileAbsolutePathCalled = true;
    return stubbedGetFileAbsolutePath;
  }

  public String getFileName(File file) {
    getFileNameCalled = true;
    return stubbedGetFileName;
  }

  public void stubExists(boolean stubbedValue) {
    stubbedExists = stubbedValue;
  }

  public void stubIsFile(boolean stubbedValue) {
    stubbedIsFile = stubbedValue;
  }

  public void stubList(String[] stubbedValue) {
    stubbedList = stubbedValue;
  }

  public void stubGetName(String stubbedValue) {
    stubbedGetName = stubbedValue;
  }

  public void stubGetFileName(String stubbedValue) {
    stubbedGetFileName = stubbedValue;
  }

  public void stubGetAbsolutePath(String stubbedValue) {
    stubbedGetAbsolutePath = stubbedValue;
  }

  public void stubGetFileAbsolutePath(String stubbedValue) {
    stubbedGetFileAbsolutePath = stubbedValue;
  }

}