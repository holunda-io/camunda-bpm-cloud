package org.camunda.bpm.extension.cloud.broadcaster;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventServiceClient {

  public static final Logger LOGGER = LoggerFactory.getLogger(EventServiceClient.class);
  private final RestTemplate rest = new RestTemplate();

  @Value(value = "${eventServiceBaseUrl}")
  private String baseUrl;

  public void broadcastEvent(final DelegateTask task) {

    final String url = baseUrl + "/task";

    String result = rest.postForObject(url, fromTask(task), String.class);

    LOGGER.info("Result: {}", result);
  }

  public static HttpEntity<String> fromTask(final DelegateTask task) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new HttpEntity<String>("{ \"taskDefinitionKey\" : \"foo\", \"taskId\": \"bar\", \"formKey\" : \"form\" }",
        headers);
  }

}
