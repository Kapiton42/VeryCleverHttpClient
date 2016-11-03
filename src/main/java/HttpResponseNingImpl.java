import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.Response;

public class HttpResponseNingImpl implements HttpResponse {

  private final int statusCode;
  private final String statusText;
  private final byte[] responseBody;
  private final HttpHeaders headers;

  public HttpResponseNingImpl(Response response) {
    statusCode = response.getStatusCode();
    statusText = response.getStatusText();
    responseBody = response.getResponseBodyAsBytes();
    headers = response.getHeaders();
  }

  @Override
  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String getStatusText() {
    return statusText;
  }

  @Override
  public byte[] getResponseBody() {
    return responseBody;
  }

  @Override
  public String getHeader(String name) {
    return headers.get(name);
  }
}
