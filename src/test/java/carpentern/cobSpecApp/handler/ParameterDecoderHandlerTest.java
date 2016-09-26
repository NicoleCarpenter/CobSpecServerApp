import carpentern.cobSpecApp.handler.ParameterDecoderHandler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.HttpResponseBuilder;
import java.util.HashMap;

public class ParameterDecoderHandlerTest extends junit.framework.TestCase {
  private ParameterDecoderHandler handler;
  private HttpResponse response;
  private Formatter formatter;

  protected void setUp() {
    formatter = new Formatter();
    HashMap<String, String> params = new HashMap<>();
    params.put("variable_2", "stuff");
    HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
    handler = new ParameterDecoderHandler(responseBuilder);
    HttpRequest request = new HttpRequest("mockMethod", "mockUri", params, "HTTP/1.1", new HashMap<String, String>(), "mockBody");    
    response = handler.handleRoute(request);
  }

  public void testHandleRoute() {
    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals("variable_2 = stuff\n", formatter.bodyToString(response));
  }
}
