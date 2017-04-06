package org.camunda.bpm.extension.cloud.workload.service.task.query.repository;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskMarkedForCompletionEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskSentToBeCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObjectStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskQueryObjectUpdater {

  @Autowired
  TaskQueryObjectRepository taskQueryObjectRepository;

  @EventHandler
  public void on(TaskCompletedEvent taskCompletedEvent){
    taskQueryObjectRepository.delete(taskCompletedEvent.getTaskId());
    log.info("TaskCompletedEvent received for task with taskId: {}", taskCompletedEvent.getTaskId());
  }

  @EventHandler
  public void on(TaskMarkedForCompletionEvent taskMarkedForCompletionEvent){
    log.info("TaskMarkedForCompletionEvent received for task with taskId: {}", taskMarkedForCompletionEvent.getTaskId());
    final TaskQueryObject task = taskQueryObjectRepository.findOne(taskMarkedForCompletionEvent.getTaskId());
    if (task != null && task.getEventType() == TaskQueryObjectStateEnum.CREATED) {
      task.setEventType(task.getEventType().next());
      taskQueryObjectRepository.save(task);
      log.info(task.toString());
    }
  }

  @EventHandler
  public void on(TaskCreatedEvent taskCreatedEvent){
    taskQueryObjectRepository.save(TaskQueryObject.from(taskCreatedEvent));
    log.info("TaskCreatedEvent received for task with taskId: {}", taskCreatedEvent.getTaskId());
  }

  @EventHandler
  public void on(TaskSentToBeCompletedEvent taskSentToBeCompletedEvent){
    final TaskQueryObject task = taskQueryObjectRepository.findOne(taskSentToBeCompletedEvent.getTaskId());
    task.setEventType(task.getEventType().next());
    taskQueryObjectRepository.save(task);
    log.info("TaskSentToBeCompletedEvent received for task with taskId: {}", taskSentToBeCompletedEvent.getTaskId());
    log.info(task.toString());
  }

}
