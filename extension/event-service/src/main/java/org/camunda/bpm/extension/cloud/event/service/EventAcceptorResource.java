package org.camunda.bpm.extension.cloud.event.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventAcceptorResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventAcceptorResource.class);

  @Autowired
  private EventCache eventCache;

  @RequestMapping(consumes = "application/json", produces = "application/json", value = "/task", method = RequestMethod.POST)
  public String eventReceived(@RequestBody(required = true) final TaskEvent task) {
    if(task.getEventType().equals("CREATED")){
      eventCache.putEvent(task);
    } else if(task.getEventType().equals("COMPLETED")){
      eventCache.removeEvent(task);
    } else {
      LOGGER.info("Task with unknown eventType {} received {}", task.getEventType(), task);
    }

    return "{'status' : 'ok'}";
  }

}
