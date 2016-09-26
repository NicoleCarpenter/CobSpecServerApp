import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.HttpResponseBuilder;
import carpentern.coreServer.handler.Handler;
import java.util.HashMap;

public class MockHandler implements Handler {
  private boolean handleRouteCalled = false;

  public HttpResponse handleRoute(HttpRequest request) {
    handleRouteCalled = true;
    HttpResponse response = buildMockResponse();
    return response;
  }

  public boolean handleRouteCalled() {
    return handleRouteCalled;
  }

  private HttpResponse buildMockResponse() {
    HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
    byte[] emptyBody = new byte[0];
    responseBuilder.buildOkResponse();
    responseBuilder.setBody(emptyBody);
    return responseBuilder.getResponse();
  }
}