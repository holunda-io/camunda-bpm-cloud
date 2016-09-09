package org.camunda.bpm.extension.cloud.event.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.event.service.EventCache;
import org.camunda.bpm.extension.cloud.event.service.client.CamundaClientFactory;
import org.camunda.bpm.extension.cloud.event.service.client.CamundaRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@Slf4j
public class TaskResource {

  @Autowired
  private EventCache eventCache;

  @Autowired
  private CamundaClientFactory camundaClientBuilder;

  @RequestMapping(produces = "application/json", value = "/task", method = RequestMethod.GET)
  public HttpEntity<Collection<Task>> getTasks() {
    final Collection<Task> events = eventCache.getEvents();
    return new HttpEntity<Collection<Task>>(events);
  }

  @RequestMapping(produces = "application/json", value = "/task/{taskId}/complete", method = RequestMethod.POST)
  public HttpEntity<String> completeTask(@PathVariable("taskId") final String taskId, @RequestBody final String body) {

    log.info("Completing task {}", taskId);
    // FIXME functional!
    final Task event = eventCache.getEvent(taskId);
    if (event != null && event.getEventType() == TaskStateEnum.CREATED) {
      event.setEventType(event.getEventType().next());
      final Optional<CamundaRestClient> clientForEngine = camundaClientBuilder.getClientForEngine(event.getEngineId());
      if (clientForEngine.isPresent()) {
        log.info("Completing task {} on the engine", taskId, event.getEngineId());
        clientForEngine.get().completeTask(taskId, body);
        event.setEventType(event.getEventType().next());
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        log.warn("No Engine found for id {}", event.getEngineId());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }

    log.info("taskState: " +event.getEventType());
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }

}
