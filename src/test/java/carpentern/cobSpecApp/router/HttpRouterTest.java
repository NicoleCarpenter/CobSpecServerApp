import carpentern.cobSpecApp.handler.*;
import carpentern.cobSpecApp.router.HttpRouter;
import carpentern.cobSpecApp.router.Routes;
import carpentern.cobSpecApp.util.SetUp;

import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponseBuilder;
import carpentern.coreServer.util.FileTypeMatcher;
import java.util.HashMap;
import java.io.File;

public class HttpRouterTest extends junit.framework.TestCase {
  private HttpRequest request;
  private HttpRouter router;
  private MockHttpFileSystem fileSystem;

  protected void setUp() {
    FileTypeMatcher typeMatcher = new FileTypeMatcher();
    File rootDirectory = new File("/public");
    fileSystem = new MockHttpFileSystem();
    MockHttpFileIO fileIO = new MockHttpFileIO();
    HttpResponseBuilder responseBuilder = new HttpResponseBuilder();

    SetUp setUp = new SetUp();
    setUp.registerRoutes(responseBuilder, fileSystem, fileIO, typeMatcher);

    router = new HttpRouter(rootDirectory, fileSystem, fileIO, responseBuilder, typeMatcher);
  }

  protected void tearDown() {
    fileSystem = null;
  }

  public void testGetRootRoute() {
    fileSystem.stubExists(true);
    request = new HttpRequest("GET", "/", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof FileHandler);
  }

  public void testGetParametersRoute() {
    request = new HttpRequest("GET", "/parameters", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof ParameterDecoderHandler);
  }

  public void testGetRouteOptions() {
    request = new HttpRequest("OPTIONS", "/method_options", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof MethodOptionsHandler);
  }

  public void testGetRouteHead() {
    fileSystem.stubExists(true);
    request = new HttpRequest("HEAD", "/", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof HeadHandler);
  }

  public void testGetRouteHeadNotFound() {
    fileSystem.stubExists(false);
    request = new HttpRequest("HEAD", "/nonExistantFile", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof NotFoundHandler);
  }

  public void testGetRouteFile() {
    fileSystem.stubExists(true);
    request = new HttpRequest("GET", "/index.txt", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof FileHandler);
  }

  public void testGetRouteFileNotFound() {
    fileSystem.stubExists(false);
    request = new HttpRequest("GET", "/nonExistantFile", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof NotFoundHandler);
  }

  public void testGetRouteBadRequest() {
    request = new HttpRequest("PUT", "/file1", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof MethodNotAllowedHandler);
  }

  public void testGetRouteBadRequestNotAMethod() {
    request = new HttpRequest("BADMETHOD", "/file1", new HashMap<>(), "HTTP/1.1", new HashMap<>(), "");
    Handler handler = router.getRoute(request);
    assertTrue(handler instanceof MethodNotAllowedHandler);
  }

}
