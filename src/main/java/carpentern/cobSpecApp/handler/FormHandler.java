package carpentern.cobSpecApp.handler;

import carpentern.cobSpecApp.file.FileIO;
import carpentern.cobSpecApp.file.FileSystem;
import carpentern.cobSpecApp.response.ResponseBuilder;
import carpentern.cobSpecApp.util.HtmlFormatter;
import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
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
      getForm();
    } else if (method.equals("POST")) {
      postForm(request);
    } else if (method.equals("PUT")) {
      putForm(request);
    } else {
      deleteForm();
    }
    return responseBuilder.getResponse();
  }

  private void getForm() {
    String form = generateForm();
    responseBuilder.buildOkResponse();

    if (fileSystem.exists(path)) {
      byte[] fileContent = fileIO.getFileContents(path);
      responseBuilder.setBody(fileContent);
    } else {
      byte[] formContent = form.getBytes();
      responseBuilder.setBody(formContent);
    }
  }

  private void postForm(HttpRequest request) {
    fileIO.writeToFile(path, request.getBody());
    responseBuilder.buildOkResponse();
  }

  private void putForm(HttpRequest request) {
    fileIO.updateFile(path, request.getBody());
    responseBuilder.buildOkResponse();
  }

  private void deleteForm() {
    fileIO.deleteFileContent(path);
    responseBuilder.buildOkResponse();
  }

  private String generateForm() {
    HtmlFormatter formatter = new HtmlFormatter();
    return formatter.createForm();
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
