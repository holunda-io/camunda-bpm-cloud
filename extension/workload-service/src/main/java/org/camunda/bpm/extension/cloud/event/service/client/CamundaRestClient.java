package org.camunda.bpm.extension.cloud.event.service.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface CamundaRestClient {

  @Headers("Content-Type: application/json")
  @RequestLine("POST /rest/engine/default/task/{taskId}/complete")
  void completeTask(@Param("taskId") String taskId, String body);
}
