import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CleverHTTPClient {
  private final BalancingAsyncHttpClient balancingAsyncHttpClient;

  public CleverHTTPClient(AsyncHttpClientAdapter asyncHttpClientAdapter, Properties hostGroup) {
    this.balancingAsyncHttpClient = new BalancingAsyncHttpClient(asyncHttpClientAdapter, hostGroup);
  }

  public static void main(String[] args) throws UnirestException, ExecutionException, InterruptedException {
    Properties properties = new Properties();
    properties.put("search", "http://google.com,http://ya.ru,http://rambler.ru");
    CleverHTTPClient cleverHTTPClient = new CleverHTTPClient(new HttpAdapterNingImpl(), properties);

    System.out.println(new String(cleverHTTPClient.prepareGet("search", "").doRequest().get().getResponseBody()));
  }

  public HttpRequest prepareGet(String hostGroup, String url) {

    return new HttpRequest(balancingAsyncHttpClient, hostGroup, url);
  }
}
