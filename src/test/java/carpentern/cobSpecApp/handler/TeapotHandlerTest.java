import carpentern.cobSpecApp.handler.TeapotHandler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.HttpResponseBuilder;
import java.util.HashMap;

public class TeapotHandlerTest extends junit.framework.TestCase {
  private TeapotHandler handler;
  private HttpResponse response;
  private Formatter formatter;

  protected void setUp() {
    formatter = new Formatter();
    HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
    handler = new TeapotHandler(responseBuilder);
  }

  public void testHandleRouteCoffee() {
    HttpRequest request = new HttpRequest("mockMethod", "/coffee", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "mockBody");
    response = handler.handleRoute(request);
    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("418", response.getStatusCode());
    assertEquals("Teapot", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals("I'm a teapot", formatter.bodyToString(response));
  }

  public void testHandleRouteTea() {
    HttpRequest request = new HttpRequest("mockMethod", "/tea", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "mockBody");
    response = handler.handleRoute(request);
    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals("", formatter.bodyToString(response));
  }
}