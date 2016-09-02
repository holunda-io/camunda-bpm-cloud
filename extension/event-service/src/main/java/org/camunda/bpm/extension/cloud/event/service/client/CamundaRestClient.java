package org.camunda.bpm.extension.cloud.event.service.client;

import feign.Param;
import feign.RequestLine;

public interface CamundaRestClient {

  @RequestLine("POST /rest/engine/default/task/{taskId}/complete")
  String completeTask(@Param("taskId") String taskId);
}
