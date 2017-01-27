package org.camunda.bpm.extension.cloud.event.service.domain;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.camunda.bpm.extension.cloud.event.service.acceptor.command.CreateTaskCommand;
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

  @EventSourcingHandler
  public void on(TaskCreatedEvent taskCreatedEvent){
    this.taskId = taskCreatedEvent.getTaskId();
  }
}
