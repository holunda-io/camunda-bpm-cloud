package org.camunda.bpm.extension.cloud.broadcaster;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Command for sending events using hystrix.
 */
public class BroadcastEventCommand extends HystrixCommand<String> {

  public static final Logger LOGGER = LoggerFactory.getLogger(BroadcastEventCommand.class);

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
      LOGGER.error("Error: ",e);
      throw e;
    }
  }

  @Override
  protected String getFallback() {
    LOGGER.error("Broadcast of event failed.");
    return "{'status' : 'failed'}";
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("url", url)
      .append("request", request)
      .append("rest", rest)
      .toString();
  }
}
