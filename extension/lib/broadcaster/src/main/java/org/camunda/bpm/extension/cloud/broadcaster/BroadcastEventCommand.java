package org.camunda.bpm.extension.cloud.broadcaster;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import lombok.extern.slf4j.Slf4j;

/**
 * Command for sending events using hystrix.
 */
@Slf4j
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
    try {
      return rest.postForObject(url, request, String.class);
    } catch (Exception e) {
      log.error("Error: ", e);
      throw e;
    }
  }

  @Override
  protected String getFallback() {
    log.error("Broadcast of event failed.");
    return "{'status' : 'failed'}";
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("url", url).append("request", request).append("rest", rest).toString();
  }
}
