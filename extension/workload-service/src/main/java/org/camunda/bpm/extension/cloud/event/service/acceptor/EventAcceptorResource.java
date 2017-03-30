package org.camunda.bpm.extension.cloud.event.service.acceptor;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.camunda.bpm.extension.cloud.event.service.EventCache;
import org.camunda.bpm.extension.cloud.event.service.acceptor.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.event.service.rest.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EventAcceptorResource {

  @Autowired
  private EventCache eventCache;

  @Autowired
  private CommandGateway commandGateway;

  /*
  public EventAcceptorResource(EventCache eventCache, CommandGateway commandGateway){
    this.eventCache = eventCache;
    this.commandGateway = commandGateway;
  }*/

  @RequestMapping(consumes = "application/json", produces = "application/json", value = "/task", method = RequestMethod.POST)
  public HttpEntity<String> eventReceived(@RequestBody(required = true) final TaskEvent taskEvent) {
    switch (taskEvent.getEventType()) {
      case "CREATED":
        CreateTaskCommand createTaskCommand = new CreateTaskCommand();
        BeanUtils.copyProperties(taskEvent, createTaskCommand);
        commandGateway.send(createTaskCommand);
        break;
      case "COMPLETED":
        eventCache.removeEvent(Task.from(taskEvent));
        break;
      default:
        log.info("Task with unknown eventType {} received {}", taskEvent.getEventType(), taskEvent);
        break;
    }
    return new HttpEntity<>("{'status' : 'ok'}");
  }

}
