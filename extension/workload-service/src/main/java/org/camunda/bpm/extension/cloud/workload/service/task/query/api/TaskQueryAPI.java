package org.camunda.bpm.extension.cloud.workload.service.task.query.api;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.repository.TaskQueryObjectCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
public class TaskQueryAPI {

  @Autowired
  private TaskQueryObjectCache taskQueryObjectCache;

  @RequestMapping(produces = "application/json", value = "/task", method = RequestMethod.GET)
  public HttpEntity<Collection<TaskQueryObject>> getTasks() {
    final Collection<TaskQueryObject> events = taskQueryObjectCache.getEvents();
    return new HttpEntity<>(events);
  }



}
