package carpentern.cobSpecApp.util;

import java.util.ArrayList;

public class HttpMethods {
  public static String get = "GET";
  public static String post = "POST";
  public static String put = "PUT";
  public static String patch = "PATCH";
  public static String head = "HEAD";
  public static String delete = "DELETE";
  public static String options = "OPTIONS";

  public static ArrayList<String> getServerMethods() {
    ArrayList<String> serverMethods = new ArrayList<String>();
    serverMethods.add(get);
    serverMethods.add(post);
    serverMethods.add(put);
    serverMethods.add(patch);
    serverMethods.add(head);
    serverMethods.add(delete);
    serverMethods.add(options);
    return serverMethods;
  }
}