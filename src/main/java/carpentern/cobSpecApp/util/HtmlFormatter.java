package carpentern.cobSpecApp.util;

public class HtmlFormatter {

  public byte[] getFormattedDirectoryFiles(String[] files, String uri) {
    String htmlHead = "<!DOCTYPE html><html><body>";
    String htmlFoot = "</body></html>";
    return (htmlHead + buildFileLinks(files, uri) + htmlFoot).getBytes();
  }

  public String createForm() {
    return "<!DOCTYPE html>\n" +
           "<html>\n" +
           "<body>\n" +
             "<form action=\"/form\" method=\"post\">" +
               "<input name=\"data\" type=\"text\">" +
               "<input type=\"submit\" value=\"submit\">" +
             "</form>" +
           "</body>" +
           "</html>";
  }

  private String buildFileLinks(String[] files, String uri) {
    StringBuilder directoryContents = new StringBuilder();
    for (String file : files) {
      directoryContents.append(buildDirectoryLink(file, uri));
    }
    return directoryContents.toString();
  }

  private String buildDirectoryLink(String file, String uri) {
    String pathSlash = "";
    if (!uri.endsWith("/")) {
      pathSlash = "/";
    }
    return "<a href=\"" + uri + pathSlash + file + "\">" + file + "</a><br>";
  }

}