package org.camunda.bpm.extension.cloud.workload.service.task.command;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCreatedEvent;
import org.springframework.beans.BeanUtils;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class TaskAggregate {

  @AggregateIdentifier
  private String taskId;

  @CommandHandler
  public TaskAggregate(CreateTaskCommand createTaskCommand){
    log.info("CreateTaskCommand received: {}", createTaskCommand);
    this.taskId = createTaskCommand.getTaskId();
    TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent();
    BeanUtils.copyProperties(createTaskCommand, taskCreatedEvent);
    apply(taskCreatedEvent);
  }

  @CommandHandler
  public void on(CompleteTaskCommand completeTaskCommand){
    log.info("CompleteTaskCommand received: {}", completeTaskCommand);
    TaskCompletedEvent taskCompletedEvent = new TaskCompletedEvent();
    BeanUtils.copyProperties(completeTaskCommand, taskCompletedEvent);
    apply(taskCompletedEvent);
  }

  @EventSourcingHandler
  public void on(TaskCreatedEvent taskCreatedEvent){
    this.taskId = taskCreatedEvent.getTaskId();
  }

  @EventSourcingHandler
  public void on(TaskCompletedEvent taskCompletedEvent) {
    this.taskId = taskCompletedEvent.getTaskId();
  }
}
