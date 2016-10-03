package carpentern.cobSpecApp;

import carpentern.cobSpecApp.file.FileTypeMatcher;
import carpentern.cobSpecApp.file.HttpFileIO;
import carpentern.cobSpecApp.file.HttpFileSystem;
import carpentern.cobSpecApp.parser.ArgumentParser;
import carpentern.cobSpecApp.response.CobSpecResponseBuilder;
import carpentern.cobSpecApp.router.HttpRouter;
import carpentern.cobSpecApp.util.SetUp;

import carpentern.coreServer.io.HttpServerOutput;
import carpentern.coreServer.parser.HttpParamParser;
import carpentern.coreServer.request.HttpRequestParser;
import carpentern.coreServer.server.HttpServer;
import carpentern.coreServer.socket.HttpServerSocket;

import java.net.ServerSocket;
import java.io.IOException;
import java.io.File;

public class Main {

  public static void main(String args[]) {
    ArgumentParser argsParser = new ArgumentParser(args);
    Integer port = argsParser.getPort();
    ServerSocket serverSocket;

    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    HttpServerSocket httpServerSocket = new HttpServerSocket(serverSocket);
    HttpServerOutput httpServerOutput = new HttpServerOutput();
    HttpParamParser httpParamParser = new HttpParamParser();
    HttpRequestParser httpRequestParser = new HttpRequestParser(httpParamParser);
    File rootDirectory = new File(argsParser.getRootDirectory());
    HttpFileSystem fileSystem = new HttpFileSystem();
    HttpFileIO fileIO = new HttpFileIO(rootDirectory);
    CobSpecResponseBuilder cobSpecResponseBuilder = new CobSpecResponseBuilder();
    FileTypeMatcher typeMatcher = new FileTypeMatcher();

    SetUp setUp = new SetUp();
    setUp.registerRoutes(cobSpecResponseBuilder, fileSystem, fileIO, typeMatcher);
    HttpRouter httpRouter = new HttpRouter(cobSpecResponseBuilder);

    HttpServer server = new HttpServer(httpServerSocket, httpRequestParser, httpRouter, httpServerOutput);

    server.start();
  }
}
