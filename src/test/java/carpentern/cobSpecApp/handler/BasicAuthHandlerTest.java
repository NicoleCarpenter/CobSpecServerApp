import carpentern.cobSpecApp.handler.BasicAuthHandler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import java.util.HashMap;
import java.util.Arrays;
import carpentern.coreServer.util.RequestLogger;

public class BasicAuthHandlerTest extends junit.framework.TestCase {
  private MockHttpResponseBuilder responseBuilder;
  private BasicAuthHandler handler;
  private HashMap<String, String> requestHeaders;
  private static String incorrectUsername = "basic dXNlcjpodW50ZXIy";
  private static String incorrectPassphrase = "basic YWRtaW46aHVudGVyMQ==";
  private static String incorrectFormat = "basic YWRtaW58aHVudGVyMg==";
  private static String incorrectFormat2 = "YWRtaW46aHVudGVyMg==";
  private static String correctCredentials = "basic YWRtaW46aHVudGVyMg==";

  protected void setUp() {
    responseBuilder = new MockHttpResponseBuilder();
    handler = new BasicAuthHandler(responseBuilder);
    requestHeaders = new HashMap<String, String>();
  }

  protected void tearDown() {
    requestHeaders = null;
    RequestLogger.clear();
  }

  public void testHandleRouteNoAuthHeader() {
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    HttpResponse response = handler.handleRoute(request);

    assertNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteNotAuthorizedIncorrectUsername() {
    requestHeaders.put("Authorization", incorrectUsername);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    HttpResponse response = handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteNotAuthorizedIncorrectPassphrase() {
    requestHeaders.put("Authorization", incorrectPassphrase);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    HttpResponse response = handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteNotAuthorizedIncorrectFormatColon() {
    requestHeaders.put("Authorization", incorrectFormat);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    HttpResponse response = handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteNotAuthorizedIncorrectFormatSpace() {
    requestHeaders.put("Authorization", incorrectFormat2);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    HttpResponse response = handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteAuthorized() {
    RequestLogger.clear();
    RequestLogger.log(new HttpRequest("GET", "/index", new HashMap<>(), "HTTP/1.1", new HashMap<String, String>(), ""));
    RequestLogger.log(new HttpRequest("OPTIONS", "/method_options", new HashMap<>(), "HTTP/1.1", new HashMap<String, String>(), ""));
    
    requestHeaders.put("Authorization", correctCredentials);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    HttpResponse response = handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildOkResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("GET /index HTTP/1.1\nOPTIONS /method_options HTTP/1.1\n", new String(responseBuilder.setBodyCalledWith));
  }

}