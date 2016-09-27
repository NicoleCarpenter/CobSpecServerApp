import carpentern.cobSpecApp.util.HtmlFormatter;

public class HtmlFormatterTest extends junit.framework.TestCase {
  HtmlFormatter formatter;

  protected void setUp() {
    formatter = new HtmlFormatter();
  }

  public void testGetFormattedDirectoryFiles() {
    String[] files = {"file1", "file2.txt", "image1.jpeg", "directory1"};
    String uri = "/public";
    assertEquals("<!DOCTYPE html><html><body>" +
                   "<a href=\"/public/file1\">file1</a><br>" +
                   "<a href=\"/public/file2.txt\">file2.txt</a><br>" +
                   "<a href=\"/public/image1.jpeg\">image1.jpeg</a><br>" +
                   "<a href=\"/public/directory1\">directory1</a><br>" +
                   "</body></html>",
                  new String(formatter.getFormattedDirectoryFiles(files, uri)));
  }

  public void testCreateForm() {
    assertEquals("<!DOCTYPE html>\n<html>\n<body>\n<form action=\"/form\" method=\"post\"><input name=\"data\" type=\"text\"><input type=\"submit\" value=\"submit\"></form></body></html>",
                  formatter.createForm());
  }

}