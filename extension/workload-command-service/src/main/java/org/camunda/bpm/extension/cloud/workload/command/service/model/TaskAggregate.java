package org.camunda.bpm.extension.cloud.workload.command.service.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.camunda.bpm.extension.cloud.workload.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.DeleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.MarkTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.command.SendTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.event.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskDeletedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskMarkedForCompletionEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskSentToBeCompletedEvent;
import org.springframework.beans.BeanUtils;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class TaskAggregate {

  @AggregateIdentifier
  @SuppressWarnings("unused")
  private String taskId;

  @CommandHandler
  public TaskAggregate(CreateTaskCommand command) {
    log.info("CreateTaskCommand received: {}", command);

    TaskCreatedEvent event = new TaskCreatedEvent();
    BeanUtils.copyProperties(command, event);
    apply(event);
  }

  @CommandHandler
  public void on(CompleteTaskCommand command) {
    log.info("CompleteTaskCommand received: {}", command);
    TaskCompletedEvent event = new TaskCompletedEvent();
    BeanUtils.copyProperties(command, event);
    apply(event);
  }

  @CommandHandler
  public void on(MarkTaskForCompletionCommand command) {
    log.info("MarkTaskForCompletionCommand received: {}", command);
    TaskMarkedForCompletionEvent event = new TaskMarkedForCompletionEvent();
    BeanUtils.copyProperties(command, event);
    apply(event);
  }

  @CommandHandler
  public void on(SendTaskForCompletionCommand command) {
    log.info("SendTaskForCompletionCommand received: {}", command);
    TaskSentToBeCompletedEvent event = new TaskSentToBeCompletedEvent();
    BeanUtils.copyProperties(command, event);
    apply(event);
  }

  @CommandHandler
  public void on(DeleteTaskCommand command) {
    log.info("DeleteTaskCommand received: {}", command);
    TaskDeletedEvent event = new TaskDeletedEvent();
    BeanUtils.copyProperties(command, event);
    apply(event);
  }

  @EventSourcingHandler
  public void on(TaskCreatedEvent taskCreatedEvent) {
    this.taskId = taskCreatedEvent.getTaskId();
  }

  @EventSourcingHandler
  public void on(TaskCompletedEvent taskCompletedEvent) {
    this.taskId = taskCompletedEvent.getTaskId();
  }

  @EventSourcingHandler
  public void on(TaskMarkedForCompletionEvent taskMarkedForCompletionEvent) {
    this.taskId = taskMarkedForCompletionEvent.getTaskId();
  }

  @EventSourcingHandler
  public void on(TaskSentToBeCompletedEvent taskSentToBeCompletedEvent) {
    this.taskId = taskSentToBeCompletedEvent.getTaskId();
  }
}
