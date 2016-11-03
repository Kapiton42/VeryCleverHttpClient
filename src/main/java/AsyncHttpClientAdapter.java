import java.util.concurrent.CompletableFuture;

public interface AsyncHttpClientAdapter {
  CompletableFuture<HttpResponse> get(String url);
}
