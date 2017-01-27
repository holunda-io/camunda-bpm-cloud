package org.camunda.bpm.extension.cloud.event.service.taskservice;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.camunda.bpm.extension.cloud.event.service.EventCache;
import org.camunda.bpm.extension.cloud.event.service.domain.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.event.service.rest.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskCreatedEventHandler {

  @Autowired
  EventCache eventCache;

  @EventHandler
  public void on(TaskCreatedEvent taskCreatedEvent){
    eventCache.putEvent(Task.from(taskCreatedEvent));
    log.info("Event received {}", taskCreatedEvent.getTaskId());
  }
}
