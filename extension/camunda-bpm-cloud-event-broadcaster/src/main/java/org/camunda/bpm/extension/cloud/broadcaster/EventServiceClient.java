package org.camunda.bpm.extension.cloud.broadcaster;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Client for transmitting task events to the task event service.
 */
@Component
public class EventServiceClient {

  public static final Logger LOGGER = LoggerFactory.getLogger(EventServiceClient.class);

  /**
   * Event type.
   */
  public static enum EventType {
    CREATED, COMPLETED, DELETED
  }

  @Value(value = "${eventServiceBaseUrl}")
  private String baseUrl;

  /**
   * Sends the task event to the event service.
   *
   * @param task
   *          task delegate.
   * @param eventType
   *          event type.
   * @param formKey
   *          form key of the task.
   */
  public void broadcastEvent(final DelegateTask task, final EventType eventType, final String formKey) {
    final String url = baseUrl + "/task";
    final String result = new BroadcastEventCommand(url, fromTask(task, eventType, formKey)).execute();
    LOGGER.info("Result: {}", result);
  }

  /**
   * Creates a JSON representation of the task.
   *
   * @param task
   *          task delegate
   * @return HTTP entity to be sent to the event service.
   */
  public static HttpEntity<String> fromTask(final DelegateTask task, final EventType eventType, final String formKey) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new HttpEntity<String>(new JSONObject() //
        .put("taskDefinitionKey", task.getTaskDefinitionKey()) //
        .put("taskId", task.getId()) //
        .put("formKey", formKey) //
        .put("engineUrl", "http://localhost:8080/") //
        .put("eventType", eventType.name()) //
        .toString(), headers);
  }

}
