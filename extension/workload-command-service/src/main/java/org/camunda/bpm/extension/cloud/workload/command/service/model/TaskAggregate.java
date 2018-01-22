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
import org.camunda.bpm.extension.cloud.workload.event.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskDeletedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskMarkedForCompletionEvent;
import org.springframework.beans.BeanUtils;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class TaskAggregate {

  @AggregateIdentifier
  private String taskId;

  @CommandHandler
  public TaskAggregate(final CreateTaskCommand command) {
    log.info("CreateTaskCommand received: {}", command);

    final TaskCreatedEvent event = new TaskCreatedEvent();
    BeanUtils.copyProperties(command, event);
    apply(event);
  }

  @CommandHandler
  public void on(final CompleteTaskCommand command) {
    log.info("CompleteTaskCommand received: {}", command);
    final TaskCompletedEvent event = new TaskCompletedEvent();
    BeanUtils.copyProperties(command, event);
    apply(event);
  }

  @CommandHandler
  public void on(final MarkTaskForCompletionCommand command) {
    log.info("MarkTaskForCompletionCommand received: {}", command);
    final TaskMarkedForCompletionEvent event = new TaskMarkedForCompletionEvent();
    event.setTaskId(command.getTaskId());
    apply(event);
  }

  @CommandHandler
  public void on(final DeleteTaskCommand command) {
    log.info("DeleteTaskCommand received: {}", command);
    final TaskDeletedEvent event = new TaskDeletedEvent();
    BeanUtils.copyProperties(command, event);
    apply(event);
  }

  @EventSourcingHandler
  public void on(final TaskCreatedEvent taskCreatedEvent) {
    this.taskId = taskCreatedEvent.getTaskId();
  }

}
