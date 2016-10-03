import carpentern.cobSpecApp.response.CobSpecResponseBuilder;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.handler.Handler;

class MockHandler implements Handler {
  private boolean handleRouteCalled = false;

  public HttpResponse handleRoute(HttpRequest request) {
    handleRouteCalled = true;
    return buildMockResponse();
  }

  public boolean handleRouteCalled() {
    return handleRouteCalled;
  }

  private HttpResponse buildMockResponse() {
    CobSpecResponseBuilder responseBuilder = new CobSpecResponseBuilder();
    byte[] emptyBody = new byte[0];
    responseBuilder.buildOkResponse();
    responseBuilder.setBody(emptyBody);
    return responseBuilder.getResponse();
  }
}