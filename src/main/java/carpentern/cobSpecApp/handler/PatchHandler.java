package carpentern.cobSpecApp.handler;

import carpentern.cobSpecApp.file.FileIO;
import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.ResponseBuilder;
import java.io.File;

public class PatchHandler implements Handler {
  private ResponseBuilder responseBuilder;
  private FileIO fileIO;
  private String etag;

  public PatchHandler(ResponseBuilder responseBuilder, FileIO fileIO) {
    this.responseBuilder = responseBuilder;
    this.fileIO = fileIO;
    this.etag = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
  }

  @Override
  public HttpResponse handleRoute(HttpRequest request) {
    String path = findPath(request);
    String requestEtag = getRequestEtag(request);
    fileIO.writeToFile(path, request.getBody());

    if (requestEtag != null && isMatchingPatchTag(requestEtag)) {
      buildPatchedContentResponse();
    } else {
      buildFullFileContentResponse(path);
    }

    responseBuilder.setHeader("Etag", etag);
    return responseBuilder.getResponse();
  }

  private String getRequestEtag(HttpRequest request) {
    return request.getHeaderLines().get("If-Match");
  }

  private boolean isMatchingPatchTag(String requestEtag) {
    return requestEtag.trim().equals(etag.trim());
  }

  private void buildPatchedContentResponse() {
    byte[] emptyBody = new byte[0];
    responseBuilder.buildPatchedContentResponse();
    responseBuilder.setBody(emptyBody);
  }

  private void buildFullFileContentResponse(String path) {
    byte[] fileContent = fileIO.getFileContents(path);
    responseBuilder.buildOkResponse();
    responseBuilder.setBody(fileContent);
  }

  private String findPath(HttpRequest request) {
    String uri = request.getUri();
    File rootDirectory = fileIO.getRootDirectory();
    String rootPath = rootDirectory.getAbsolutePath();
    String requestPath = uri.replace(rootDirectory.getName(), "");
    return rootPath + requestPath;
  }

}
