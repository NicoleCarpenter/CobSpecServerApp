package carpentern.cobSpecApp.util;

import carpentern.coreServer.request.HttpRequest;
import java.util.ArrayList;

public class RequestLogger {
  private static ArrayList<String> requests = new ArrayList<>();

  public static void log(HttpRequest request) {
    requests.add(request.getRequestStartLine());
  }

  public static ArrayList<String> loggedRequests() {
    return (ArrayList<String>) requests.clone();
  }

  public static void clear() {
    requests.clear();
  }
}