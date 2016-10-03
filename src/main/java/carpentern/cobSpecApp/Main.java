package carpentern.cobSpecApp;

import carpentern.cobSpecApp.file.FileTypeMatcher;
import carpentern.cobSpecApp.file.HttpFileIO;
import carpentern.cobSpecApp.file.HttpFileSystem;
import carpentern.cobSpecApp.parser.ArgumentParser;
import carpentern.cobSpecApp.router.HttpRouter;
import carpentern.cobSpecApp.util.SetUp;

import carpentern.coreServer.io.HttpServerOutput;
import carpentern.coreServer.parser.HttpParamParser;
import carpentern.coreServer.request.HttpRequestParser;
import carpentern.coreServer.response.HttpResponseBuilder;
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
    HttpRequestParser httpRequestParser = new HttpRequestParser(httpServerOutput, httpParamParser);
    File rootDirectory = new File(argsParser.getRootDirectory());
    HttpFileSystem fileSystem = new HttpFileSystem();
    HttpFileIO fileIO = new HttpFileIO(rootDirectory);
    HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();
    FileTypeMatcher typeMatcher = new FileTypeMatcher();

    SetUp setUp = new SetUp();
    setUp.registerRoutes(httpResponseBuilder, fileSystem, fileIO, typeMatcher);
    HttpRouter httpRouter = new HttpRouter(httpResponseBuilder);

    HttpServer server = new HttpServer(httpServerSocket, httpRequestParser, httpRouter, httpServerOutput);

    server.start();
  }
}
