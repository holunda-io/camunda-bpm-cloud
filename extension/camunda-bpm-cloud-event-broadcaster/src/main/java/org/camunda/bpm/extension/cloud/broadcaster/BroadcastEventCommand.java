package org.camunda.bpm.extension.cloud.broadcaster;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class BroadcastEventCommand extends HystrixCommand<String> {

  private final String url;
  private final HttpEntity<String> request;

  private final RestTemplate rest = new RestTemplate();

  public BroadcastEventCommand(String url, HttpEntity<String> request) {
    super(HystrixCommandGroupKey.Factory.asKey("BroadcastEvent"));
    this.url = url;
    this.request = request;
  }

  @Override
  protected String run() throws Exception {
    return rest.postForObject(url, request, String.class);
  }
}
