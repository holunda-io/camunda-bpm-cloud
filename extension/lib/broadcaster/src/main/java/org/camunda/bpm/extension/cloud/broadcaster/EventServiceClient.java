package org.camunda.bpm.extension.cloud.broadcaster;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Client for transmitting task events to the task event service.
 */
@Component
@Slf4j
@Deprecated
public class EventServiceClient {

  /**
   * Event type.
   */
  public static enum EventType {
    CREATED, COMPLETED, DELETED
  }

  @Autowired
  private EurekaClient discoveryClient;

  @Value(value = "${spring.application.name}")
  private String appName;

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
    // TODO: 1. replace by workloadcommandservice as soon as it is separated from the query service
    // TODO: 2. remove the REST call completely and replace it by the axon command to the exchange
    final InstanceInfo instance = discoveryClient.getNextServerFromEureka("workloadqueryservice", false);

    final String url = instance.getHomePageUrl() + "/task";

    final BroadcastEventCommand command = new BroadcastEventCommand(url, fromTask(task, eventType, formKey, appName));

    log.info("sending {}", command);
    final String result = command.execute();

    log.info("Result: {}", result);
  }

  /**
   * Creates a JSON representation of the task.
   *
   * @param task
   *          task delegate
   * @return HTTP entity to be sent to the event service.
   */
  public static HttpEntity<String> fromTask(final DelegateTask task, final EventType eventType, final String formKey,
      final String engineId) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<String>(new JSONObject() //
        .put("assignee", task.getAssignee()) //
        .put("caseDefinitionId", task.getCaseDefinitionId()) //
        .put("caseExecutionId", task.getCaseExecutionId()) //
        .put("createTime", task.getCreateTime()) //
        .put("description", task.getDescription()) //
        .put("dueDate", task.getDueDate()) //
        .put("formKey", formKey) //
        .put("engineId", engineId) //
        .put("name", task.getName()) //
        .put("eventType", eventType.name()) //
        .put("owner", task.getOwner()) //
        .put("priority", task.getPriority()) //
        .put("processInstanceId", task.getProcessInstanceId()) //
        .put("taskDefinitionKey", task.getTaskDefinitionKey()) //
        .put("tenantId", task.getTenantId()) //
        .put("taskId", task.getId()) //
        .toString(), headers);
  }

}