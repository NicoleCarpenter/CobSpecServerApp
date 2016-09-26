import carpentern.coreServer.router.Router;
import carpentern.coreServer.response.HttpResponseBuilder;
import carpentern.coreServer.file.FileSystem;
import carpentern.coreServer.io.FileIO;
import carpentern.coreServer.util.FileTypeMatcher;
import carpentern.coreServer.util.SetUp;

public class SetUpTest extends junit.framework.TestCase {
  private MockHttpRouter router;
  private HttpResponseBuilder responseBuilder;
  private MockHttpFileSystem fileSystem;
  private MockHttpFileIO fileIO;
  private FileTypeMatcher typeMatcher;
  private SetUp setUp;

  protected void setUp() {
    router = new MockHttpRouter();
    responseBuilder = new HttpResponseBuilder();
    fileSystem = new MockHttpFileSystem();
    fileIO = new MockHttpFileIO();
    typeMatcher = new FileTypeMatcher();
    setUp = new SetUp();
  }

  public void testRegisterRoutes() {
    setUp.registerRoutes(responseBuilder, fileSystem, fileIO, typeMatcher);
    assertTrue(true);
  }

}