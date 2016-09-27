package carpentern.cobSpecApp;

import carpentern.cobSpecApp.util.SetUp;
import carpentern.cobSpecApp.router.HttpRouter;

import carpentern.coreServer.file.HttpFileSystem;
import carpentern.coreServer.io.HttpServerOutput;
import carpentern.coreServer.io.HttpFileIO;
import carpentern.coreServer.request.HttpRequestParser;
import carpentern.coreServer.response.HttpResponseBuilder;
import carpentern.coreServer.parser.HttpParamParser;
import carpentern.coreServer.server.HttpServer;
import carpentern.coreServer.socket.HttpServerSocket;
import carpentern.coreServer.util.ArgumentParser;
import carpentern.coreServer.util.FileTypeMatcher;

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
    HttpFileIO fileIO = new HttpFileIO(rootDirectory, fileSystem);
    HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();
    FileTypeMatcher typeMatcher = new FileTypeMatcher();

    SetUp setUp = new SetUp();
    setUp.registerRoutes(httpResponseBuilder, fileSystem, fileIO, typeMatcher);
    HttpRouter httpRouter = new HttpRouter(rootDirectory, fileSystem, fileIO, httpResponseBuilder, typeMatcher);

    HttpServer server = new HttpServer(httpServerSocket, httpRequestParser, httpRouter, httpServerOutput);

    server.start();
  }
}
