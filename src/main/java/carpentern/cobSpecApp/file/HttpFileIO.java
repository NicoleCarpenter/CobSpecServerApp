package carpentern.cobSpecApp.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

public class HttpFileIO implements FileIO {
  private File rootDirectory;

  public HttpFileIO(File rootDirectory) {
    this.rootDirectory = rootDirectory;
  }

  @Override
  public byte[] getFileContents(String filePath) {
    FileInputStream fileInputStream;
    File file = new File(filePath);
    byte[] fileContent = new byte[getFileLength(file)];
    try {
      fileInputStream = new FileInputStream(file);
      fileInputStream.read(fileContent);
      fileInputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileContent;
  }

  public byte[] getPartialFileContents(String filePath, String rawRange) {
    int fileLength = getFileLength(new File(filePath));
    byte[] fileContents = getFileContents(filePath);
    int[] range = splitRange(rawRange, fileLength);
    return Arrays.copyOfRange(fileContents, range[0], range[1]);
  }

  private int getFileLength(File file) {
    return (int) file.length();
  }

  private int[] splitRange(String range, int fileLength) {
    String rawRange = getRangeFromHeader(range);
    String[] rangeLimits = getRangeLimits(rawRange);
    return getRange(rangeLimits, fileLength);
  }

  private String getRangeFromHeader(String range) {
    return range.split("=")[1];
  }

  private String[] getRangeLimits(String rawRange) {
    return rawRange.trim().split("-");
  }

  private int[] getRange(String[] range, int fileLength) {
    int rangeStart = 0;
    int rangeEnd = fileLength;
    if (isRangeWithoutEnd(range)) {
      rangeStart = getRangeLimitAsInt(range, 0);
    } else if (isRangeWithoutStart(range)) {
      rangeStart = fileLength - getRangeLimitAsInt(range, 1);
    } else {
      rangeStart = getRangeLimitAsInt(range, 0);
      rangeEnd = getRangeLimitAsInt(range, 1) + 1;
    }
    return new int[] {rangeStart, rangeEnd};
  }

  private boolean isRangeWithoutEnd(String[] range) {
    return range.length == 1;
  }

  private boolean isRangeWithoutStart(String[] range) {
    return range[0].isEmpty();
  }

  private int getRangeLimitAsInt(String[] range, int index) {
    try {
      return Integer.parseInt(range[index]);
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  public void writeToFile(String filePath, String content) {
    try {
      PrintWriter writer = new PrintWriter(filePath, "UTF-8");
      writer.println(content);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void updateFile(String filePath, String content) {
    File file = new File(filePath);
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(file));
      writer.write(content);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void deleteFileContent(String fileName){
    FileOutputStream fileOutputStream;
    File file = new File(fileName);
    try {
      fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write("".getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public File getRootDirectory() {
    return rootDirectory;
  }

}