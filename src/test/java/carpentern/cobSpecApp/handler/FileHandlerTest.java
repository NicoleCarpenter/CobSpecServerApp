import carpentern.cobSpecApp.file.FileTypeMatcher;
import carpentern.cobSpecApp.handler.FileHandler;

import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import java.util.HashMap;
import java.io.File;

public class FileHandlerTest extends junit.framework.TestCase {
  private FileHandler handler;
  private HttpRequest request;
  private String responseBody;
  private MockHttpFileIO fileIO;
  private MockHttpFileSystem fileSystem;
  private MockHttpResponseBuilder responseBuilder;
  private HashMap<String, String> requestHeaders;

  protected void setUp() {
    responseBuilder = new MockHttpResponseBuilder();
    fileSystem = new MockHttpFileSystem();
    fileSystem.stubGetFileAbsolutePath("/");
    fileSystem.stubGetFileName("root");
    fileIO = new MockHttpFileIO();
    FileTypeMatcher typeMatcher = new FileTypeMatcher();
    handler = new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher);
    requestHeaders = new HashMap<>();
  }

  private HttpResponse testResponse(String uri, boolean isFile, String responseBody) {
    request = new HttpRequest("GET", uri, new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    fileSystem.stubIsFile(isFile);
    fileIO.stubResponseBody(responseBody);
    return handler.handleRoute(request);
  }

  public void testHandleRouteSideEffects() {
    String uri = "/file.txt";
    boolean isFile = true;
    String responseBody = "This is a file";
    HttpResponse response = testResponse(uri, isFile, responseBody);

    assertTrue(fileIO.getRootDirectoryCalled);
    assertTrue(fileSystem.isFileCalled);
    assertTrue(fileSystem.getFileAbsolutePathCalled);
    assertTrue(fileSystem.getFileNameCalled);
    assertTrue(responseBuilder.setHeaderCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertTrue(responseBuilder.getResponseCalled);
  }

  public void testHandleRouteIsFile() {
    String uri = "/file.txt";
    boolean isFile = true;
    String responseBody = "This is a file";
    HttpResponse response = testResponse(uri, isFile, responseBody);

    assertTrue(fileIO.getFileContentsCalled);
    assertTrue(responseBuilder.buildOkResponseCalled);
    assertEquals("This is a file", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRoutePartialFile() {
    String uri = "/file.txt";
    boolean isFile = true;
    requestHeaders.put("Range", "bytes=0-4");
    String responseBody = "This ";
    HttpResponse response = testResponse(uri, isFile, responseBody);

    assertTrue(fileIO.getPartialFileContentsCalled);
    assertEquals("//file.txt bytes=0-4", fileIO.getPartialFileContentsCalledWith);
    assertTrue(responseBuilder.buildPartialFileResponseCalled);
    assertEquals("This ", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteIsDirectory() {
    String uri = "/file.txt";
    boolean isFile = false;
    String responseBody = "This is a file";
    String[] directoryFiles = {"File1", "File2", "File3"};
    fileSystem.stubList(directoryFiles);
    HttpResponse response = testResponse(uri, isFile, responseBody);

    assertTrue(fileSystem.listCalled);
    assertTrue(responseBuilder.buildOkResponseCalled);
    assertEquals("<!DOCTYPE html><html><body><a href=\"/file.txt/File1\">File1</a><br><a href=\"/file.txt/File2\">File2</a><br><a href=\"/file.txt/File3\">File3</a><br></body></html>",
                  new String(responseBuilder.setBodyCalledWith));
  }

}