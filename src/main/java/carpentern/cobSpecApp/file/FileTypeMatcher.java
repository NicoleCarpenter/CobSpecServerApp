package carpentern.cobSpecApp.file;

public class FileTypeMatcher {

  public String getFileType(String uri) {
    int dotPosition = uri.lastIndexOf(".");
    return findFileType(getExtension(dotPosition, uri));
  }

  private String findFileType(String extension) {
    if (isText(extension)) {
      return "text/html";
    } else if (isImageJpg(extension)) {
      return "image/jpeg";
    } else if (isImageGif(extension)) {
      return "image/gif";
    } else if (isImagePng(extension)) {
      return "image/png";
    } else {
      return "application/octet-stream";
    }
  }

  private String getExtension(int dotPosition, String uri) {
    return uri.substring(dotPosition);
  }

  private boolean isText(String extension) {
    return extension.equals(".htm") || extension.equals(".html") || extension.equals(".txt");
  }

  private boolean isImageJpg(String extension) {
    return extension.equals(".jpg") || extension.equals(".jpeg");
  }

  private boolean isImageGif(String extension) {
    return extension.equals(".gif");
  }

  private boolean isImagePng(String extension) {
    return extension.equals(".png");
  }
}