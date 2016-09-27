import carpentern.cobSpecApp.file.FileTypeMatcher;

public class FileTypeMatcherTest extends junit.framework.TestCase {
  FileTypeMatcher typeMatcher;

  protected void setUp() {
    typeMatcher = new FileTypeMatcher();
  }

  public void testGetFileTypeHtm() {
    String uri = "/file.htm";
    assertEquals("text/html", typeMatcher.getFileType(uri));
  }

  public void testGetFileTypeHtml() {
    String uri = "/file.html";
    assertEquals("text/html", typeMatcher.getFileType(uri));
  }

  public void testGetFileTypeTxt() {
    String uri = "/file.txt";
    assertEquals("text/html", typeMatcher.getFileType(uri));
  }

  public void testGetFileTypeJpg() {
    String uri = "/file.jpg";
    assertEquals("image/jpeg", typeMatcher.getFileType(uri));
  }

  public void testGetFileTypeJpeg() {
    String uri = "/file.jpeg";
    assertEquals("image/jpeg", typeMatcher.getFileType(uri));
  }

  public void testGetFileTypeGif() {
    String uri = "/file.gif";
    assertEquals("image/gif", typeMatcher.getFileType(uri));
  }

  public void testGetFileTypePng() {
    String uri = "/file.png";
    assertEquals("image/png", typeMatcher.getFileType(uri));
  }

  public void testGetFileTypeOther() {
    String uri = "/file.pdf";
    assertEquals("application/octet-stream", typeMatcher.getFileType(uri));
  }

}