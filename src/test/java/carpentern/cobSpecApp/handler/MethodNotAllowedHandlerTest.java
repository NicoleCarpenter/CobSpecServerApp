import carpentern.cobSpecApp.handler.MethodNotAllowedHandler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.cobSpecApp.response.CobSpecResponseBuilder;
import java.util.HashMap;

public class MethodNotAllowedHandlerTest extends junit.framework.TestCase {
  private MethodNotAllowedHandler handler;
  private HttpResponse response;
  private Formatter formatter;

  protected void setUp() {
    formatter = new Formatter();
    CobSpecResponseBuilder responseBuilder = new CobSpecResponseBuilder();
    handler = new MethodNotAllowedHandler(responseBuilder);
    HttpRequest request = new HttpRequest("mockMethod", "mockUri", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "mockBody");
    response = handler.handleRoute(request);
  }

  public void testHandleRoute() {
    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("405", response.getStatusCode());
    assertEquals("Method not allowed", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals("", formatter.bodyToString(response));
  }
}
