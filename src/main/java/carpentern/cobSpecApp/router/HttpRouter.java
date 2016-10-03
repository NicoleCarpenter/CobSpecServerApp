package carpentern.cobSpecApp.router;

import carpentern.cobSpecApp.handler.*;
import carpentern.cobSpecApp.util.Config;
import carpentern.cobSpecApp.util.RequestLogger;
import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.ResponseBuilder;
import carpentern.coreServer.router.Router;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpRouter implements Router {
  private ResponseBuilder responseBuilder;

  public HttpRouter(ResponseBuilder responseBuilder) {
    this.responseBuilder = responseBuilder;
  }

  @Override
  public Handler getRoute(HttpRequest request) {
    RequestLogger.log(request);
    Handler handler;
    String method = request.getMethod();
    String uri = request.getUri();
    Routes configRoutes = Config.routes;
    HashMap<String, Handler> route = configRoutes.getRouteForUri(uri);

    if (route != null) {
      ArrayList<String> methodOptions = configRoutes.getMethodOptions(uri);
      if (method.equals("OPTIONS")) {
        handler = new MethodOptionsHandler(responseBuilder, methodOptions);
      } else if (methodOptions.contains(method)) {
        handler = route.get(method);
      } else {
        handler = new MethodNotAllowedHandler(responseBuilder);
      }
    } else {
      handler = new NotFoundHandler(responseBuilder);
    }
    return handler;
  }

}