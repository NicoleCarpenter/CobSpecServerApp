import carpentern.cobSpecApp.handler.FormHandler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.HttpResponseBuilder;
import java.util.HashMap;

public class FormHandlerTest extends junit.framework.TestCase {
  private HttpResponse response;
  private String responseBody;
  private MockHttpFileIO fileIO;
  private MockHttpFileSystem fileSystem;
  private HttpResponseBuilder responseBuilder;
  private Formatter formatter;
  private String method;
  private Boolean exists;
  private HashMap<String, String> testHeaders;

  protected void setUp() {
    formatter = new Formatter();
    fileIO = new MockHttpFileIO();
    fileSystem = new MockHttpFileSystem();
    responseBuilder = new HttpResponseBuilder();
    testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");
  }

  protected void tearDown() {
    fileIO = null;
    fileSystem = null;
    responseBuilder = null;
  }

  private HttpResponse testHandlerResponse(String responseBody, String method, Boolean exists) {
    fileIO.stubResponseBody(responseBody);
    fileSystem.stubExists(exists);

    FormHandler handler = new FormHandler(responseBuilder, fileSystem, fileIO);
    HttpRequest request = new HttpRequest(method, "/form", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    
    return handler.handleRoute(request);
  }

  public void testHandleRouteGetResponse() {
    responseBody = "Any file contents";
    method = "GET";
    exists = true;

    response = testHandlerResponse(responseBody, method, exists);

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals(responseBody, formatter.bodyToString(response));
  }

  public void testHandleRouteGetSideEffects() {
    responseBody = "Any file contents";
    method = "GET";
    exists = true;

    response = testHandlerResponse(responseBody, method, exists);

    assertTrue(fileIO.getRootDirectoryCalled);
    assertTrue(fileSystem.existsCalled);
    assertTrue(fileIO.getFileContentsCalled);
    assertFalse(fileIO.writeToFileCalled);
    assertFalse(fileIO.updateFileCalled);
  }

  public void testHandleRouteGetResponseDoesNotExist() {
    responseBody = "<!DOCTYPE html>\n<html>\n<body>\n<form action=\"/form\" method=\"post\"><input name=\"data\" type=\"text\"><input type=\"submit\" value=\"submit\"></form></body></html>";
    method = "GET";
    exists = false;

    response = testHandlerResponse(responseBody, method, exists);

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals(responseBody, formatter.bodyToString(response));
  }

  public void testHandleRouteGetSideEffectsDoesNotExist() {
    responseBody = "<!DOCTYPE html>\n<html>\n<body>\n<form action=\"/form\" method=\"post\"><input name=\"data\" type=\"text\"><input type=\"submit\" value=\"submit\"></form></body></html>";
    method = "GET";
    exists = false;

    response = testHandlerResponse(responseBody, method, exists);

    assertTrue(fileIO.getRootDirectoryCalled);
    assertTrue(fileSystem.existsCalled);
    assertFalse(fileIO.getFileContentsCalled);
  }

  public void testHandleRoutePostResponse() {
    responseBody = "";
    method = "POST";
    exists = true;

    response = testHandlerResponse(responseBody, method, exists);

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
  }

  public void testHandleRoutePostSideEffects() {
    responseBody = "";
    method = "POST";
    exists = true;

    response = testHandlerResponse(responseBody, method, exists);

    assertTrue(fileIO.getRootDirectoryCalled);
    assertFalse(fileSystem.existsCalled);
    assertTrue(fileIO.writeToFileCalled);
  }

  public void testHandleRoutePutResponse() {
    responseBody = "";
    method = "PUT";
    exists = true;

    response = testHandlerResponse(responseBody, method, exists);

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
  }

  public void testHandleRoutePutSideEffects() {
    responseBody = "";
    method = "PUT";
    exists = true;

    response = testHandlerResponse(responseBody, method, exists);

    assertTrue(fileIO.getRootDirectoryCalled);
    assertFalse(fileSystem.existsCalled);
    assertTrue(fileIO.updateFileCalled);
  }

  public void testHandleRouteDeleteResponse() {
    responseBody = "";
    method = "DELETE";
    exists = true;

    response = testHandlerResponse(responseBody, method, exists);

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
  }

  public void testHandleRouteDeleteSideEffects() {
    responseBody = "";
    method = "DELETE";
    exists = true;

    response = testHandlerResponse(responseBody, method, exists);

    assertTrue(fileIO.getRootDirectoryCalled);
    assertFalse(fileSystem.existsCalled);
    assertTrue(fileIO.deleteFileContentCalled);
  }

}