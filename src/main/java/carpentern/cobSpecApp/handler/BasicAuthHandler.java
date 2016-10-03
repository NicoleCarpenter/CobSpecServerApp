package carpentern.cobSpecApp.handler;

import carpentern.cobSpecApp.util.RequestLogger;
import carpentern.coreServer.handler.Handler;
import carpentern.coreServer.request.HttpRequest;
import carpentern.coreServer.response.HttpResponse;
import carpentern.coreServer.response.ResponseBuilder;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class BasicAuthHandler implements Handler {
  private ResponseBuilder responseBuilder;
  private String defaultUsername;
  private String defaultPassword;

  public BasicAuthHandler(ResponseBuilder responseBuilder) {
    this.responseBuilder = responseBuilder;
    this.defaultUsername = "admin";
    this.defaultPassword = "hunter2";
  }

  public HttpResponse handleRoute(HttpRequest request) {
    String authorizationHeader = request.getHeaderLines().get("Authorization");
    try {
      if (isAuthorized(authorizationHeader)) {
        handleAuthorizedRequest();
      } else {
        handleUnauthorizedRequest();
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return responseBuilder.getResponse();
  }

  private boolean isAuthorized(String authorizationHeader) throws UnsupportedEncodingException {
    return isHeaderIncludesAuthRequest(authorizationHeader) && isMatchingRequest(authorizationHeader);
  }

  private boolean isHeaderIncludesAuthRequest(String authorizationHeader) {
    return authorizationHeader != null && authorizationHeader.contains(" ");
  }

  private boolean isMatchingRequest(String authorizationHeader) throws UnsupportedEncodingException {
    String encodedUsernameAndPass = getEncodedUsernameAndPass(authorizationHeader);
    String decodedUsernameAndPass = decode(encodedUsernameAndPass);
    return isProperlyFormattedUsernameAndPass(decodedUsernameAndPass) && isUsernameAndPassValid(decodedUsernameAndPass);
  }

  private String getEncodedUsernameAndPass(String authorizationHeader) {
    return authorizationHeader.split(" ")[1];
  }

  private String decode(String encodedUsernameAndPass) throws UnsupportedEncodingException {
    return new String(Base64.getMimeDecoder().decode(encodedUsernameAndPass), "UTF-8");
  }

  private boolean isProperlyFormattedUsernameAndPass(String decodedUsernameAndPass) {
    return decodedUsernameAndPass.contains(":");
  }

  private boolean isUsernameAndPassValid(String decodedUsernameAndPass) {
    String[] usernameAndPass = splitUsernameAndPass(decodedUsernameAndPass);
    return isUsernameMatching(usernameAndPass) && isPassphraseMatching(usernameAndPass);
  }

  private String[] splitUsernameAndPass(String decodedUsernameAndPass) {
    return decodedUsernameAndPass.split(":");
  }

  private boolean isUsernameMatching(String[] usernameAndPass) {
    return usernameAndPass[0].equals(defaultUsername);
  }

  private boolean isPassphraseMatching(String[] usernameAndPass) {
    return usernameAndPass[1].equals(defaultPassword);
  }

  private void handleAuthorizedRequest() {
    byte[] logData = getLogData().getBytes();
    responseBuilder.buildOkResponse();
    responseBuilder.setBody(logData);
  }

  private void handleUnauthorizedRequest() {
    byte[] unauthorizedMessage = "Unauthorized".getBytes();
    responseBuilder.buildUnauthorizedResponse();
    responseBuilder.setBody(unauthorizedMessage);
  }

  private String getLogData() {
    String logData = "";
    for (String requestLine : RequestLogger.loggedRequests()) {
      logData += requestLine + "\n";
    }
    return logData;
  }
}
