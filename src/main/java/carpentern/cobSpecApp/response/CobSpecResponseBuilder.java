package carpentern.cobSpecApp.response;

import carpentern.coreServer.response.HttpResponse;
import carpentern.cobSpecApp.response.ResponseBuilder;

public class CobSpecResponseBuilder implements ResponseBuilder {
  private HttpResponse response;

  public CobSpecResponseBuilder() {
    response = new HttpResponse();
  }

  @Override
  public HttpResponse getResponse() {
    return response;
  }

  public void setStatusCode(String code) {
    response.setStatusCode(code);
  }

  public void setStatusMessage(String message) {
    response.setStatusMessage(message);
  }

  public void setDefaultHeaders() {
    response.setHeader("Server", "Nicole's HTTP server");
  }

  public void setHeader(String key, String value) {
    response.setHeader(key, value);
  }

  public void setBody(byte[] bodyContent) {
    response.setBody(bodyContent);
  }

  public void buildOkResponse() {
    setStatusCode("200");
    setStatusMessage("OK");
    setDefaultHeaders();
  }

  public void buildPartialFileResponse(String range) {
    setStatusCode("206");
    setStatusMessage("Partial Content");
    setHeader("Content-Range", range);
    setDefaultHeaders();
  }

  public void buildUnauthorizedResponse() {
    setStatusCode("401");
    setStatusMessage("Unauthorized");
    setHeader("WWW-Authenticate", "Basic realm=\"Server\"");
    setDefaultHeaders();
  }

  public void buildMethodNotAllowedResponse() {
    setStatusCode("405");
    setStatusMessage("Method not allowed");
    setDefaultHeaders();
  }

  public void buildNotFoundResponse() {
    setStatusCode("404");
    setStatusMessage("Not Found");
    setDefaultHeaders();
  }

  public void buildPatchedContentResponse() {
    setStatusCode("204");
    setStatusMessage("Patched Content");
    setDefaultHeaders();
  }

  public void buildRedirectResponse() {
    setStatusCode("302");
    setStatusMessage("REDIRECT");
    setHeader("Location", "http://localhost:5000/");
    setDefaultHeaders();
  }

  public void buildCoffeeResponse() {
    setStatusCode("418");
    setStatusMessage("Teapot");
    setDefaultHeaders();
  }

}