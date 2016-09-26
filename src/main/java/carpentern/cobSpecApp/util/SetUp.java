package carpentern.cobSpecApp.util;

import carpentern.coreServer.file.FileSystem;
import carpentern.coreServer.io.FileIO;
import carpentern.cobSpecApp.handler.*;
import carpentern.coreServer.response.HttpResponseBuilder;
import carpentern.coreServer.router.Router;
import carpentern.coreServer.router.Routes;
import carpentern.coreServer.util.FileTypeMatcher;

public class SetUp {

  public void registerRoutes(HttpResponseBuilder responseBuilder, FileSystem fileSystem, FileIO fileIO, FileTypeMatcher typeMatcher) {
    Config.routes.addRoute("/", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/image.jpeg", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/image.png", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/image.gif", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/text-file.txt", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/file1", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/method_options", "GET", new DefaultHandler(responseBuilder));
    Config.routes.addRoute("/method_options", "HEAD", new DefaultHandler(responseBuilder));
    Config.routes.addRoute("/method_options", "POST", new DefaultHandler(responseBuilder));
    Config.routes.addRoute("/method_options", "PUT", new DefaultHandler(responseBuilder));
    Config.routes.addRoute("/method_options", "DELETE", new DefaultHandler(responseBuilder));
    Config.routes.addRoute("/method_options", "OPTIONS", new DefaultHandler(responseBuilder));
    Config.routes.addRoute("/method_options2", "GET", new DefaultHandler(responseBuilder));
    Config.routes.addRoute("/method_options2", "OPTIONS", new DefaultHandler(responseBuilder));
    Config.routes.addRoute("/form", "GET", new FormHandler(responseBuilder, fileSystem, fileIO));
    Config.routes.addRoute("/form", "PUT", new FormHandler(responseBuilder, fileSystem, fileIO));
    Config.routes.addRoute("/form", "POST", new FormHandler(responseBuilder, fileSystem, fileIO));
    Config.routes.addRoute("/form", "DELETE", new FormHandler(responseBuilder, fileSystem, fileIO));
    Config.routes.addRoute("/parameters", "GET", new ParameterDecoderHandler(responseBuilder));
    Config.routes.addRoute("/redirect", "GET", new RedirectHandler(responseBuilder));
    Config.routes.addRoute("/redirect", "Redirect", new RedirectHandler(responseBuilder));
    Config.routes.addRoute("/logs", "GET", new BasicAuthHandler(responseBuilder));
    Config.routes.addRoute("/tea", "GET", new TeapotHandler(responseBuilder));
    Config.routes.addRoute("/coffee", "GET", new TeapotHandler(responseBuilder));
    Config.routes.addRoute("/index.txt", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/partial_content.txt", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/patch-content.txt", "GET", new FileHandler(responseBuilder, fileSystem, fileIO, typeMatcher));
    Config.routes.addRoute("/patch-content.txt", "PATCH", new PatchHandler(responseBuilder, fileIO));
    Config.routes.addRoute("/", "HEAD", new HeadHandler(responseBuilder));
  }
}