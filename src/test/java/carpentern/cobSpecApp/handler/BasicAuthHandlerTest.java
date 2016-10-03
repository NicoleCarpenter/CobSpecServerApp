import carpentern.cobSpecApp.handler.BasicAuthHandler;
import carpentern.cobSpecApp.util.RequestLogger;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import java.util.HashMap;
import java.util.Arrays;

public class BasicAuthHandlerTest extends junit.framework.TestCase {
  private MockCobSpecResponseBuilder responseBuilder;
  private BasicAuthHandler handler;
  private HashMap<String, String> requestHeaders;

  protected void setUp() {
    responseBuilder = new MockCobSpecResponseBuilder();
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
    handler.handleRoute(request);

    assertNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteNotAuthorizedIncorrectUsername() {
    String incorrectUsername = "basic dXNlcjpodW50ZXIy";
    requestHeaders.put("Authorization", incorrectUsername);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteNotAuthorizedIncorrectPassphrase() {
    String incorrectPassphrase = "basic YWRtaW46aHVudGVyMQ==";
    requestHeaders.put("Authorization", incorrectPassphrase);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteNotAuthorizedIncorrectFormatColon() {
    String incorrectFormat = "basic YWRtaW58aHVudGVyMg==";
    requestHeaders.put("Authorization", incorrectFormat);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteNotAuthorizedIncorrectFormatSpace() {
    String incorrectFormat2 = "YWRtaW46aHVudGVyMg==";
    requestHeaders.put("Authorization", incorrectFormat2);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildUnauthorizedResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("Unauthorized", new String(responseBuilder.setBodyCalledWith));
  }

  public void testHandleRouteAuthorized() {
    String correctCredentials = "basic YWRtaW46aHVudGVyMg==";
    RequestLogger.clear();
    RequestLogger.log(new HttpRequest("GET", "/index", new HashMap<>(), "HTTP/1.1", new HashMap<>(), ""));
    RequestLogger.log(new HttpRequest("OPTIONS", "/method_options", new HashMap<>(), "HTTP/1.1", new HashMap<>(), ""));
    
    requestHeaders.put("Authorization", correctCredentials);
    
    HttpRequest request = new HttpRequest("GET", "/logs", new HashMap<>(), "HTTP/1.1", requestHeaders, "");
    String authHeader = request.getHeaderLines().get("Authorization");
    handler.handleRoute(request);

    assertNotNull(authHeader);
    assertTrue(responseBuilder.buildOkResponseCalled);
    assertTrue(responseBuilder.setBodyCalled);
    assertEquals("GET /index HTTP/1.1\nOPTIONS /method_options HTTP/1.1\n", new String(responseBuilder.setBodyCalledWith));
  }

}