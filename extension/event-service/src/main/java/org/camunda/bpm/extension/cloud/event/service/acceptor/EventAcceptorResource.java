package org.camunda.bpm.extension.cloud.event.service.acceptor;

import org.camunda.bpm.extension.cloud.event.service.EventCache;
import org.camunda.bpm.extension.cloud.event.service.rest.TaskEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EventAcceptorResource {

  @Autowired
  private EventCache eventCache;

  @RequestMapping(consumes = "application/json", produces = "application/json", value = "/task", method = RequestMethod.POST)
  public HttpEntity<String> eventReceived(@RequestBody(required = true) final TaskEvent task) {
    if (task.getEventType().equals("CREATED")) {
      eventCache.putEvent(task);
    } else if (task.getEventType().equals("COMPLETED")) {
      eventCache.removeEvent(task);
    } else {
      log.info("Task with unknown eventType {} received {}", task.getEventType(), task);
    }
    return new HttpEntity<String>("{'status' : 'ok'}");
  }

}
