import carpentern.cobSpecApp.file.FileIO;
import carpentern.cobSpecApp.file.FileSystem;
import carpentern.cobSpecApp.file.FileTypeMatcher;
import carpentern.cobSpecApp.response.CobSpecResponseBuilder;
import carpentern.cobSpecApp.util.SetUp;

import carpentern.coreServer.router.Router;

public class SetUpTest extends junit.framework.TestCase {
  private CobSpecResponseBuilder responseBuilder;
  private MockHttpFileSystem fileSystem;
  private MockHttpFileIO fileIO;
  private FileTypeMatcher typeMatcher;
  private SetUp setUp;

  protected void setUp() {
    MockHttpRouter router = new MockHttpRouter();
    responseBuilder = new CobSpecResponseBuilder();
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