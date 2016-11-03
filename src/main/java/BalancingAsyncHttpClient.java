import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

class BalancingAsyncHttpClient {
  private final AsyncHttpClientAdapter asyncHttpClientAdapter;
  private final Map<String, List<String>> hostGroupToHostList;

  BalancingAsyncHttpClient(AsyncHttpClientAdapter asyncHttpClientAdapter, Properties properties) {
    hostGroupToHostList = new ConcurrentHashMap<>();
    properties.forEach((hostGroup, hostList) ->
        hostGroupToHostList.put((String) hostGroup, Arrays.asList(((String) hostList).split(","))));
    this.asyncHttpClientAdapter = asyncHttpClientAdapter;
  }

  String getHost(String hostGroup) {
    return hostGroupToHostList.get(hostGroup).get(new Random().nextInt(hostGroupToHostList.get(hostGroup).size()));
  }

  CompletableFuture<HttpResponse> get(String groupHost, String url, int maxRetry) {
    CompletableFuture<HttpResponse> completableFuture = new CompletableFuture<>();
    CompletableFuture.runAsync(
        () -> {
          HttpResponse response = null;
          for (int i = 0; i < maxRetry; i++) {
            try {
              response = asyncHttpClientAdapter.get(getHost(groupHost) + url).get();
              if (response.getStatusCode() == 200) {
                break;
              }
            } catch (ExecutionException e) {
              completableFuture.completeExceptionally(e);
              return;
            } catch (InterruptedException e) {
              completableFuture.completeExceptionally(e);
              Thread.currentThread().interrupt();
              return;
            }
          }
          completableFuture.complete(response);
        }
    );
    return completableFuture;
  }
}
