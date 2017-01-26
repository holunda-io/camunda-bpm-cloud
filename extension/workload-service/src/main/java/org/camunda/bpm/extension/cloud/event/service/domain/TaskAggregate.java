package org.camunda.bpm.extension.cloud.event.service.domain;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.camunda.bpm.extension.cloud.event.service.acceptor.command.CreateTaskCommand;
import org.springframework.beans.BeanUtils;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@AggregateRoot
@NoArgsConstructor
public class TaskAggregate {

  @AggregateIdentifier
  private String taskId;

  @CommandHandler
  public TaskAggregate(CreateTaskCommand createTaskCommand){
    TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent();
    BeanUtils.copyProperties(createTaskCommand, taskCreatedEvent);
    apply(taskCreatedEvent);
  }

  @EventSourcingHandler
  public void on(TaskCreatedEvent taskCreatedEvent){
    this.taskId = taskCreatedEvent.getTaskId();
  }
}
