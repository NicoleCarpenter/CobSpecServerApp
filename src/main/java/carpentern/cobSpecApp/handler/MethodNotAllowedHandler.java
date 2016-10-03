package carpentern.cobSpecApp.handler;

import carpentern.cobSpecApp.response.ResponseBuilder;
import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;

public class MethodNotAllowedHandler implements Handler {
  private ResponseBuilder responseBuilder;

  public MethodNotAllowedHandler(ResponseBuilder responseBuilder) {
    this.responseBuilder = responseBuilder;
  }

  @Override
  public HttpResponse handleRoute(HttpRequest request) {
    byte[] emptyBody = new byte[0];
    responseBuilder.buildMethodNotAllowedResponse();
    responseBuilder.setBody(emptyBody);
    return responseBuilder.getResponse();
  }
}