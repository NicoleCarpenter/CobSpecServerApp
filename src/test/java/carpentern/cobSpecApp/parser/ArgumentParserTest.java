import carpentern.cobSpecApp.parser.ArgumentParser;

public class ArgumentParserTest extends junit.framework.TestCase {

  public void testGetPortGiven() {
    String[] args = new String[] {"-p", "3333"};
    ArgumentParser argParser = new ArgumentParser(args);
    assertEquals(3333, argParser.getPort());
  }

  public void testGetPortDefault() {
    String[] args = new String[] {};
    ArgumentParser argParser = new ArgumentParser(args);
    assertEquals(5000, argParser.getPort());
  }

  public void testGetRootDirectoryGiven() {
    String[] args = new String[] {"-d", "sampleDirectory/documents"};
    ArgumentParser argParser = new ArgumentParser(args);
    assertEquals("sampleDirectory/documents", argParser.getRootDirectory());
  }

  public void testGetRootDirectoryDefault() {
    String[] args = new String[] {};
    ArgumentParser argParser = new ArgumentParser(args);
    assertEquals("public", argParser.getRootDirectory());
  }

  public void testGetRootDirectoryWithPort() {
    String[] args = new String[] {"-p", "3333", "-d", "sampleDirectory/documents"};
    ArgumentParser argParser = new ArgumentParser(args);
    assertEquals(3333, argParser.getPort());
    assertEquals("sampleDirectory/documents", argParser.getRootDirectory());
  }
}
