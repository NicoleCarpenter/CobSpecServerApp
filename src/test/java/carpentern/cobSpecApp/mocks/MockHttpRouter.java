import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.router.Router;

import java.util.HashMap;

public class MockHttpRouter implements Router {
  boolean getRouteCalled = false;
  boolean registerRouteCalled = false;
  boolean registerMethodHandlerCalled = false;

  public Handler getRoute(HttpRequest request) {
    getRouteCalled = true;
    return new MockHandler();
  }
}