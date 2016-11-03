import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.util.concurrent.CompletableFuture;

public class HttpAdapterNingImpl implements AsyncHttpClientAdapter {
  private final AsyncHttpClient asyncHttpClient;

  public HttpAdapterNingImpl() {

    asyncHttpClient = new DefaultAsyncHttpClient();
  }

  @Override
  public CompletableFuture<HttpResponse> get(String url) {
    return asyncHttpClient.prepareGet(url)
        .execute()
        .toCompletableFuture()
        .thenApply(HttpResponseNingImpl::new);
  }
}
