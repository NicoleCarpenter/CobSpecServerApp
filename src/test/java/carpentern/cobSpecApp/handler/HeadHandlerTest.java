import carpentern.cobSpecApp.handler.HeadHandler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.HttpResponseBuilder;
import java.util.HashMap;

public class HeadHandlerTest extends junit.framework.TestCase {
  private HttpResponse response;
  private Formatter formatter;

  protected void setUp() {
    formatter = new Formatter();
    HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
    HeadHandler handler = new HeadHandler(responseBuilder);

    HttpRequest request = new HttpRequest("HEAD", "/", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    response = handler.handleRoute(request);
  }

  public void testHandleRoute() {
    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals("", formatter.bodyToString(response));
  }
}
