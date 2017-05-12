package org.camunda.bpm.extension.cloud.workload.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.camunda.bpm.extension.cloud.workload.service.task.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.MarkTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.SendTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.event.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.event.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.event.TaskMarkedForCompletionEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.event.TaskSentToBeCompletedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
  public TaskAggregate(CreateTaskCommand createTaskCommand) {
    log.info("CreateTaskCommand received: {}", createTaskCommand);
    this.taskId = createTaskCommand.getTaskId();
    TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent();
    BeanUtils.copyProperties(createTaskCommand, taskCreatedEvent);
    apply(taskCreatedEvent);
  }

  @CommandHandler
  public void on(CompleteTaskCommand completeTaskCommand) {
    log.info("CompleteTaskCommand received: {}", completeTaskCommand);
    TaskCompletedEvent taskCompletedEvent = new TaskCompletedEvent();
    BeanUtils.copyProperties(completeTaskCommand, taskCompletedEvent);
    apply(taskCompletedEvent);
  }

  @CommandHandler
  public void on(MarkTaskForCompletionCommand markTaskForCompletionCommand) {
    log.info("MarkTaskForCompletionCommand received: {}", markTaskForCompletionCommand);
    TaskMarkedForCompletionEvent taskMarkedForCompletionEvent = new TaskMarkedForCompletionEvent();
    BeanUtils.copyProperties(markTaskForCompletionCommand, taskMarkedForCompletionEvent);
    apply(taskMarkedForCompletionEvent);
  }

  @CommandHandler
  public void on(SendTaskForCompletionCommand sendTaskForCompletionCommand) {
    log.info("SendTaskForCompletionCommand received: {}", sendTaskForCompletionCommand);
    TaskSentToBeCompletedEvent taskSentToBeCompletedEvent = new TaskSentToBeCompletedEvent();
    BeanUtils.copyProperties(sendTaskForCompletionCommand, taskSentToBeCompletedEvent);
    apply(taskSentToBeCompletedEvent);
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
