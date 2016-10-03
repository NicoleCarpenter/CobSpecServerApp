import carpentern.cobSpecApp.handler.NotFoundHandler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.cobSpecApp.response.CobSpecResponseBuilder;
import java.util.HashMap;

public class NotFoundHandlerTest extends junit.framework.TestCase {
  private HttpResponse response;
  private Formatter formatter;

  protected void setUp() {
    formatter = new Formatter();
    CobSpecResponseBuilder responseBuilder = new CobSpecResponseBuilder();
    NotFoundHandler handler = new NotFoundHandler(responseBuilder);
    HttpRequest request = new HttpRequest("GET", "mockUri", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    response = handler.handleRoute(request);
  }

  public void testHandleRoute() {
    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("404", response.getStatusCode());
    assertEquals("Not Found", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals("", formatter.bodyToString(response));
  }
}
