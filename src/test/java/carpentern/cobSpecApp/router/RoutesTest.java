import carpentern.cobSpecApp.router.Routes;
import carpentern.cobSpecApp.util.HttpMethods;

import carpentern.coreServer.handler.Handler;
import java.util.Map;
import java.util.HashMap;

public class RoutesTest extends junit.framework.TestCase {
  Routes routes;
  Map<String, HashMap<String, Handler>> routesList;

  protected void setUp() {
    routes = new Routes();
  }

  public void testGetRouteForUriDoesNotExist() {
    routesList = routes.getRoutes();
    assertTrue(routesList.isEmpty());
  }

  public void testGetRouteForUriExists() {
    routes.addRoute("/index", HttpMethods.get, new MockHandler());
    routesList = routes.getRoutes();
    assertNotNull(routes.getRouteForUri("/index"));
    assertFalse(routesList.isEmpty());
  }

  public void testAddRouteDoesNotExist() {
    routesList = routes.getRoutes();
    assertFalse(routes.routeExists("/foo", "GET"));
  }

  public void testAddRouteExists() {
    routes.addRoute("/index", HttpMethods.get, new MockHandler());
    assertTrue(routes.routeExists("/index", "GET"));
  }

  public void testAddRouteRouteExistsNewMethod() {
    routes.addRoute("/index", HttpMethods.get, new MockHandler());
    routes.addRoute("/index", HttpMethods.post, new MockHandler());
    assertTrue(routes.routeExists("/index", "GET"));
    assertTrue(routes.routeExists("/index", "POST"));
  }

  public void testRouteExistsTrue() {
    routes.addRoute("/index", HttpMethods.get, new MockHandler());
    assertTrue(routes.routeExists("/index", "GET"));
  }

  public void testRouteExistsFalse() {
    routes.addRoute("/index", HttpMethods.get, new MockHandler());
    assertFalse(routes.routeExists("/other_page", "GET"));
  }
}