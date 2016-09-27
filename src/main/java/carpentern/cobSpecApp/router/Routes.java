package carpentern.cobSpecApp.router;

import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.util.HttpMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Routes {
  private HashMap<String, HashMap<String, Handler>> routes;

  public Routes() {
    HashMap<String, Handler> route = new HashMap<String, Handler>();
    routes = new HashMap<String, HashMap<String, Handler>>();
  }

  public HashMap<String, Handler> getRouteForUri(String uri) {
    return routes.get(uri);
  }

  public void addRoute(String uri, String method, Handler handler) {
    HashMap<String, Handler> newRoute = new HashMap<String, Handler>();
    HashMap<String, Handler> routesForUri = getRouteForUri(uri);
    if (!uriLogged(routesForUri)) {
      newRoute.put(method, handler);
      routes.put(uri, newRoute);
    } else if (!methodForUriLogged(routesForUri, method)) {
      routesForUri.put(method, handler);
    }
  }

  public HashMap<String, HashMap<String, Handler>> getRoutes() {
    return routes;
  }

  public ArrayList<String> getMethodOptions(String uri) {
    Set<String> methodSet = getRouteForUri(uri).keySet();
    ArrayList<String> methodList = new ArrayList<String>(methodSet);
    if (methodList.size() != 0) {
      return methodList;
    } else {
      return HttpMethods.getServerMethods();
    }
  }

  public boolean routeExists(String uri, String method) {
    HashMap<String, Handler> routesForUri = routes.get(uri);
    return uriLogged(routesForUri) && methodForUriLogged(routesForUri, method);
  }

  private boolean uriLogged(HashMap<String, Handler> routesForUri) {
    return routesForUri != null;
  }

  private boolean methodForUriLogged(HashMap<String, Handler> routesForUri, String method) {
    return routesForUri.get(method) != null;
  }

}