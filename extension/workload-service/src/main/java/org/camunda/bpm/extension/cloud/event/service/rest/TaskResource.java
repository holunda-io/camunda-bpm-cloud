package org.camunda.bpm.extension.cloud.event.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.event.service.EventCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Slf4j
public class TaskResource {

  @Autowired
  private EventCache eventCache;

  @RequestMapping(produces = "application/json", value = "/task", method = RequestMethod.GET)
  public HttpEntity<Collection<Task>> getTasks() {
    final Collection<Task> events = eventCache.getEvents();
    return new HttpEntity<>(events);
  }

  @RequestMapping(produces = "application/json", value = "/task/{taskId}/complete", method = RequestMethod.POST)
  public HttpEntity<String> completeTask(@PathVariable("taskId") final String taskId, @RequestBody final String body) {

    log.info("Completing task {}", taskId);
    final Task event = eventCache.getEvent(taskId);
    if (event != null && event.getEventType() == TaskStateEnum.CREATED) {
      event.setEventType(event.getEventType().next());
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
