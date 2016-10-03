package carpentern.cobSpecApp.handler;

import carpentern.cobSpecApp.response.ResponseBuilder;
import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import java.util.ArrayList;

public class MethodOptionsHandler implements Handler {
  private ResponseBuilder responseBuilder;
  private ArrayList<String> methodOptions;

  public MethodOptionsHandler(ResponseBuilder responseBuilder, ArrayList<String> methodOptions) {
    this.responseBuilder = responseBuilder;
    this.methodOptions = methodOptions;
  }

  @Override
  public HttpResponse handleRoute(HttpRequest request) {
    byte[] emptyBody = new byte[0];
    responseBuilder.buildOkResponse();
    responseBuilder.setBody(emptyBody);
    getAllowedMethodsHeader(request);
    return responseBuilder.getResponse();
  }

  private void getAllowedMethodsHeader(HttpRequest request) {
    StringBuilder builder = new StringBuilder();
    for (String item : methodOptions) {
      if (builder.length() != 0) {
        builder.append(",");
      }
      builder.append(item);
    }
    responseBuilder.setHeader("Allow", builder.toString());
  }
}