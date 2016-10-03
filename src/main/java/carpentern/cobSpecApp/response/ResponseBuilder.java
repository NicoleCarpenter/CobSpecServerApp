package carpentern.cobSpecApp.response;

import carpentern.coreServer.response.HttpResponse;

public interface ResponseBuilder {
  HttpResponse getResponse();
  void setStatusCode(String code);
  void setStatusMessage(String message);
  void setDefaultHeaders();
  void setHeader(String key, String value);
  void setBody(byte[] bodyContent);
  void buildOkResponse();
  void buildPartialFileResponse(String range);
  void buildUnauthorizedResponse();
  void buildMethodNotAllowedResponse();
  void buildNotFoundResponse();
  void buildPatchedContentResponse();
  void buildRedirectResponse();
  void buildCoffeeResponse();
}