import carpentern.cobSpecApp.file.HttpFileIO;
import carpentern.cobSpecApp.file.FileSystem;
import java.io.File;
import org.junit.Test;
import org.junit.Ignore;

@Ignore
public class HttpFileIOTest extends junit.framework.TestCase {
  private String testRoot;
  private String applicationRoot;
  private HttpFileIO fileIO;

  protected void setUp() {
    testRoot = "/Users/foo/Desktop/coding/java/applications/CobSpecServerApp/src/test/java/carpentern/cobSpecApp/testFiles";
    applicationRoot = "/Users/foo/Desktop/coding/java/applications/CobSpecServerApp/public";
    MockHttpFileSystem fileSystem = new MockHttpFileSystem();
    fileIO = new HttpFileIO(new File(testRoot), fileSystem);
  }

  protected void tearDown() {
    fileIO.deleteFileContent(testRoot + "/test-file-2.txt");
  }

  public void testGetFileContents() {
    String filePath = testRoot + "/test-file-1.txt";
    assertEquals("Some text", new String(fileIO.getFileContents(filePath)));
  }

  public void testGetPartialFileContentsStartToMid() {
    String filePath = applicationRoot + "/partial_content.txt";
    String rawRange = "Range: bytes=0-4";
    assertEquals("This ", new String(fileIO.getPartialFileContents(filePath, rawRange)));
  }

  public void testGetPartialFileContentsEnd() {
    String filePath = applicationRoot + "/partial_content.txt";
    String rawRange = "Range: bytes=-6";
    assertEquals("a 206.", new String(fileIO.getPartialFileContents(filePath, rawRange)));
  }

  public void testGetPartialFileContentsMidToEnd1() {
    String filePath = applicationRoot + "/partial_content.txt";
    String rawRange = "Range: bytes=62-";
    assertEquals("fulfill a 206.",
                 new String(fileIO.getPartialFileContents(filePath, rawRange)));
  }

  public void testGetPartialFileContentsMidToEnd2() {
    String filePath = applicationRoot + "/partial_content.txt";
    String rawRange = "Range: bytes=4-";
    assertEquals(" is a file that contains text to read part of in order to fulfill a 206.",
                 new String(fileIO.getPartialFileContents(filePath, rawRange)));
  }

  public void testGetPartialFileContentsMidToEnd3() {
    String filePath = applicationRoot + "/partial_content.txt";
    String rawRange = "Range: bytes=71-75";
    assertEquals(" 206.",
                 new String(fileIO.getPartialFileContents(filePath, rawRange)));
  }

  public void testWriteToFile() {
    String filePath = testRoot + "/test-file-2.txt";
    String content = "Hello World";
    fileIO.writeToFile(filePath, content);
    assertEquals("Hello World\n", new String(fileIO.getFileContents(filePath)));
  }

  public void testUpdateFile() {
    String filePath = testRoot + "/test-file-2.txt";
    String content = "Hello World";
    fileIO.updateFile(filePath, content);
    assertEquals("Hello World", new String(fileIO.getFileContents(filePath)));
  }

  public void testDeleteFileContent() {
    String filePath = testRoot + "/test-file-3.txt";
    String content = "contents to be deleted";

    fileIO.writeToFile(filePath, content);
    assertEquals("contents to be deleted\n", new String(fileIO.getFileContents(filePath)));

    fileIO.deleteFileContent(filePath);
    assertEquals("", new String(fileIO.getFileContents(filePath)));
  }

}