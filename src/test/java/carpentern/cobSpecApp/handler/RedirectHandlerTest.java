import carpentern.cobSpecApp.handler.RedirectHandler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.HttpResponseBuilder;
import java.util.HashMap;
import java.io.File;

public class RedirectHandlerTest extends junit.framework.TestCase {
  private HttpResponse response;
  private Formatter formatter;

  protected void setUp() {
    formatter = new Formatter();
    HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
    RedirectHandler handler = new RedirectHandler(responseBuilder);

    HttpRequest request = new HttpRequest("GET", "/redirect", new HashMap<>(), "HTTP/1.1", new HashMap<String, String>(), "");
    response = handler.handleRoute(request);
  }

  public void testHandleRoute() {
    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");
    testHeaders.put("Location", "http://localhost:5000/");

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("302", response.getStatusCode());
    assertEquals("REDIRECT", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals("", formatter.bodyToString(response));
  }
}
