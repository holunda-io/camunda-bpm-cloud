package org.camunda.bpm.extension.cloud.event.service.taskservice;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.camunda.bpm.extension.cloud.event.service.EventCache;
import org.camunda.bpm.extension.cloud.event.service.domain.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.event.service.rest.Task;
import org.camunda.bpm.extension.cloud.event.service.rest.TaskStateEnum;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class TaskCreatedEventHandler {

  @Autowired
  EventCache eventCache;

  @EventHandler
  public void on(TaskCreatedEvent taskCreatedEvent){
    Task task = Task.builder()
      .name(taskCreatedEvent.getName())
      .taskId(taskCreatedEvent.getTaskId())
      .eventType(TaskStateEnum.CREATED)
      .engineId(taskCreatedEvent.getEngineId()).build();
    eventCache.putEvent(task);
    log.info("Event created {}", taskCreatedEvent.getTaskId());
  }
}
