package org.camunda.bpm.extension.cloud.workload.service.task.query.api;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObjectStateEnum;
import org.camunda.bpm.extension.cloud.workload.service.task.query.repository.TaskQueryObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
public class TaskQueryAPI {

  @Autowired
  private TaskQueryObjectRepository taskQueryObjectRepository;

  @GetMapping(produces = "application/json", value = "/task")
  public HttpEntity<Collection<TaskQueryObject>> getTasks() {
    final Collection<TaskQueryObject> taskQueryObjects = taskQueryObjectRepository.findByEventType(TaskQueryObjectStateEnum.CREATED);
    return new HttpEntity<>(taskQueryObjects);
  }

}
