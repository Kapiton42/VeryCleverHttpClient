import java.util.concurrent.CompletableFuture;

public class HttpRequest {
  private final BalancingAsyncHttpClient balancingAsyncHttpClient;
  private final String url;
  private final String hostGroup;
  private int maxRetry = 1;

  HttpRequest(BalancingAsyncHttpClient balancingAsyncHttpClient, String hostGroup, String url) {
    this.balancingAsyncHttpClient = balancingAsyncHttpClient;
    this.url = url;
    this.hostGroup = hostGroup;
  }

  public HttpRequest withMaxRetry(int maxRetry) {
    this.maxRetry = maxRetry;
    return this;
  }

  public CompletableFuture<HttpResponse> doRequest() {
    return balancingAsyncHttpClient.get(hostGroup, url, maxRetry);
  }
}
