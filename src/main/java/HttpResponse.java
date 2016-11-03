public interface HttpResponse {
  int getStatusCode();
  String getStatusText();
  byte[] getResponseBody();
  String getHeader(String name);
}
