import carpentern.cobSpecApp.response.CobSpecResponseBuilder;
import carpentern.cobSpecApp.response.ResponseBuilder;
import carpentern.coreServer.response.HttpResponse;
import java.util.HashMap;

public class CobSpecResponseBuilderTest extends junit.framework.TestCase {
  private ResponseBuilder builder;
  private HttpResponse response;
  private Formatter formatter;

  protected void setUp() {
    formatter = new Formatter();
    builder = new CobSpecResponseBuilder();
  }

  public void testSetStatusCode() {
    builder.setStatusCode("200");
    response = builder.getResponse();
    assertEquals("200", response.getStatusCode());
  }

  public void testSetStatusMessage() {
    builder.setStatusMessage("OK");
    response = builder.getResponse();
    assertEquals("OK", response.getStatusMessage());
  }

  public void testSetDefaultHeaders() {
    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");

    builder.setDefaultHeaders();
    response = builder.getResponse();

    assertEquals(testHeaders, response.getHeaderLines());
  }

  public void testSetBody() {
    String responseBody = "This is the root";
    byte[] bodyContent = "/".getBytes();
    builder.setBody(bodyContent);
    response = builder.getResponse();
    assertEquals("/", formatter.bodyToString(response));
  }

  public void testSetBodyEmpty() {
    byte[] emptyBody = new byte[0];
    builder.setBody(emptyBody);
    response = builder.getResponse();
    assertEquals("", formatter.bodyToString(response));
  }

  public void testSetBodyMessage() {
    byte[] responseMessage = "This is a body message".getBytes();
    builder.setBody(responseMessage);
    response = builder.getResponse();
    assertEquals("This is a body message", formatter.bodyToString(response));
  }

  public void testGetResponse() {
    byte[] emptyBody = new byte[0];
    builder.setStatusCode("200");
    builder.setStatusMessage("OK");
    builder.setDefaultHeaders();
    builder.setBody(emptyBody);
    HttpResponse response = builder.getResponse();

    HashMap<String, String> testHeaders = new HashMap<>();
    testHeaders.put("Server", "Nicole's HTTP server");

    assertEquals("HTTP/1.1", response.getHttpVersion());
    assertEquals("200", response.getStatusCode());
    assertEquals("OK", response.getStatusMessage());
    assertEquals(testHeaders, response.getHeaderLines());
    assertEquals("", formatter.bodyToString(response));
  }

}
