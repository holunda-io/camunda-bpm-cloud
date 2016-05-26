package org.camunda.bpm.extension.cloud.event.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventAcceptorResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventAcceptorResource.class);

  @RequestMapping(consumes = "application/json", produces = "application/json", value = "/task", method = RequestMethod.POST)
  public String eventReceived(@RequestBody(required = true) final TaskEvent task) {
    LOGGER.info("Task received {}", task);
    return "{'status' : 'ok'}";
  }
}
