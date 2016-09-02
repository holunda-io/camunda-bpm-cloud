package org.camunda.bpm.extension.cloud.event.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.event.service.rest.TaskEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class EventCache {

  private HashMap<String, TaskEvent> eventCash;

  public EventCache() {
    this.eventCash = new HashMap<String, TaskEvent>();
  }

  public EventCache(HashMap<String, TaskEvent> eventCash) {
    this.eventCash = eventCash;
  }

  public void putEvent(TaskEvent task) {
    eventCash.put(task.getTaskId(), task);
    log.info("Event cached: {}", task);
  }

  public TaskEvent getEvent(String key) {
    return eventCash.get(key);
  }

  public Collection<TaskEvent> getEvents() {
    return this.eventCash.values();
  }

  public void removeEvent(TaskEvent task) {
    this.eventCash.remove(task.getTaskId());
    log.info("Event removed from cache: {}", task);
  }

}
