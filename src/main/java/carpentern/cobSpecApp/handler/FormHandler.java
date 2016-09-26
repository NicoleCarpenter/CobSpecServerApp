package carpentern.cobSpecApp.handler;

import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.io.FileIO;
import carpentern.coreServer.file.FileSystem;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.ResponseBuilder;
import java.util.HashMap;
import java.util.Arrays;
import java.io.File;

public class FormHandler implements Handler {
  private ResponseBuilder responseBuilder;
  private FileSystem fileSystem;
  private FileIO fileIO;
  private String path;

  public FormHandler(ResponseBuilder responseBuilder, FileSystem fileSystem, FileIO fileIO) {
    this.responseBuilder = responseBuilder;
    this.fileSystem = fileSystem;
    this.fileIO = fileIO;
    this.path = "";
  }

  @Override
  public HttpResponse handleRoute(HttpRequest request) {
    String method = request.getMethod();
    setPath(findPath(request));

    if (method.equals("GET")) {
      getForm(request);
    } else if (method.equals("POST")) {
      postForm(request);
    } else if (method.equals("PUT")) {
      putForm(request);
    } else {
      deleteForm(request);
    }
    return responseBuilder.getResponse();
  }

  public void getForm(HttpRequest request) {
    String form = generateForm();
    File file = new File(path);
    responseBuilder.buildOkResponse();

    if (fileSystem.exists(path)) {
      byte[] fileContent = fileIO.getFileContents(path);
      responseBuilder.setBody(fileContent);
    } else {
      byte[] formContent = new String(form).getBytes();
      responseBuilder.setBody(formContent);
    }
    HttpResponse response = responseBuilder.getResponse();
  }

  public void postForm(HttpRequest request) {
    fileIO.writeToFile(path, request.getBody());
    responseBuilder.buildOkResponse();
  }

  public void putForm(HttpRequest request) {
    fileIO.updateFile(path, request.getBody());
    responseBuilder.buildOkResponse();
  }

  public void deleteForm(HttpRequest request) {
    fileIO.deleteFileContent(path);
    responseBuilder.buildOkResponse();
  }

  private String generateForm() {
    return "<!DOCTYPE html>\n" +
           "<html>\n" +
           "<body>\n" +
           "<form action=\"/form\" method=\"post\">" +
           "<input name=\"data\" type=\"text\">" +
           "<input type=\"submit\" value=\"submit\">" +
           "</form>" +
           "</body></html>";
  }

  private String findPath(HttpRequest request) {
    String uri = request.getUri();
    File rootDirectory = fileIO.getRootDirectory();
    String rootPath = rootDirectory.getAbsolutePath();
    String requestPath = uri.replace(rootDirectory.getName(), "");
    return rootPath + requestPath;
  }

  private void setPath(String path) {
    this.path = path;
  }
}
