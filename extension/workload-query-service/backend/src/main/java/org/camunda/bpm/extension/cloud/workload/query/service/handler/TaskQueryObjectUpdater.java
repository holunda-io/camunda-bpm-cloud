package org.camunda.bpm.extension.cloud.workload.query.service.handler;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.camunda.bpm.extension.cloud.workload.event.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskMarkedForCompletionEvent;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObjectRepository;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObjectStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TaskQueryObjectUpdater {

  @Autowired
  TaskQueryObjectRepository taskQueryObjectRepository;

  @EventHandler
  public void on(final TaskCreatedEvent taskCreatedEvent) {
    this.taskQueryObjectRepository.save(TaskQueryObject.from(taskCreatedEvent));
    log.info("TaskCreatedEvent received for task with taskId: {}", taskCreatedEvent.getTaskId());
  }

  @EventHandler
  public void on(final TaskMarkedForCompletionEvent taskMarkedForCompletionEvent) {
    log.info("TaskMarkedForCompletionEvent received for task with taskId: {}", taskMarkedForCompletionEvent.getTaskId());
    final TaskQueryObject task = this.taskQueryObjectRepository.findOne(taskMarkedForCompletionEvent.getTaskId());
    if (taskIsCompletable(task)) {
      task.setEventType(task.getEventType().next());
      this.taskQueryObjectRepository.save(task);
      log.info(task.toString());
    }
  }

  @EventHandler
  public void on(final TaskCompletedEvent taskCompletedEvent) {
    this.taskQueryObjectRepository.delete(taskCompletedEvent.getTaskId());
    log.info("TaskCompletedEvent received for task with taskId: {}", taskCompletedEvent.getTaskId());
  }

  private boolean taskIsCompletable(final TaskQueryObject task) {
    Assert.notNull(task);
    Assert.state(task.getEventType() == TaskQueryObjectStateEnum.CREATED, "task must be in state CREATED, but was: " + task.getEventType());
    return true;
  }

}
