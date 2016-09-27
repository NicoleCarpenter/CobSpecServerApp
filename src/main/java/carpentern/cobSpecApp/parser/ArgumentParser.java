package carpentern.cobSpecApp.parser;

public class ArgumentParser {
  private int port = 5000;
  private String rootDirectory = "public";
  private String[] args;

  public ArgumentParser(String[] args) {
    this.args = args;
    setServerOptions();
  }

  public int getPort() {
    return port;
  }

  public String getRootDirectory() {
    return rootDirectory;
  }

  private void setServerOptions(){
    for (int i = 0; i < args.length; i++){
      switch (args[i]) {
        case "-p":
          setPort(i + 1);
          break;
        case "-d":
          setRootDirectory(i + 1);
          break;
      }
    }
  }

  private void setPort(int argIndex){
    port = Integer.parseInt(args[argIndex]);
  }

  private void setRootDirectory(int argIndex){
    rootDirectory = args[argIndex];
  }
}
