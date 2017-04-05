package org.camunda.bpm.extension.cloud.workload.service.task.command.api;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.MarkTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.query.repository.TaskQueryObjectCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class TaskCommandAPI {

  @Autowired
  private TaskQueryObjectCache taskQueryObjectCache;

  @Autowired
  private CommandGateway commandGateway;

  @RequestMapping(consumes = "application/json", produces = "application/json", value = "/task", method = RequestMethod.POST)
  public HttpEntity<String> taskCommandMessageReceived(@RequestBody(required = true) final TaskCommandMessage taskCommandMessage) {
    switch (taskCommandMessage.getEventType()) {
      case "CREATED":
        CreateTaskCommand createTaskCommand = new CreateTaskCommand();
        BeanUtils.copyProperties(taskCommandMessage, createTaskCommand);
        commandGateway.send(createTaskCommand);
        break;
      case "COMPLETED":
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand();
        BeanUtils.copyProperties(taskCommandMessage, completeTaskCommand);
        commandGateway.send(completeTaskCommand);
        break;
      default:
        log.info("TaskQueryObject with unknown eventType {} received {}", taskCommandMessage.getEventType(), taskCommandMessage);
        break;
    }
    return new HttpEntity<>("{'status' : 'ok'}");
  }

  @RequestMapping(produces = "application/json", value = "/task/{taskId}/complete", method = RequestMethod.POST)
  public HttpEntity<String> markTaskForCompletion(@PathVariable("taskId") final String taskId, @RequestBody final String body) {
    MarkTaskForCompletionCommand markTaskForCompletionCommand = new MarkTaskForCompletionCommand();
    markTaskForCompletionCommand.setTaskId(taskId);
    commandGateway.send(markTaskForCompletionCommand);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
