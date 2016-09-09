package org.camunda.bpm.extension.cloud.event.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.event.service.rest.Task;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class EventCache {

  private HashMap<String, Task> eventCash;

  public EventCache() {
    this.eventCash = new HashMap<String, Task>();
  }

  public EventCache(HashMap<String, Task> eventCash) {
    this.eventCash = eventCash;
  }

  public void putEvent(Task task) {
    eventCash.put(task.getTaskId(), task);
    log.info("Event cached: {}", task);
  }

  public Task getEvent(String key) {
    return eventCash.get(key);
  }

  public Collection<Task> getEvents() {
    return this.eventCash.values();
  }

  public void removeEvent(Task task) {
    this.eventCash.remove(task.getTaskId());
    log.info("Event removed from cache: {}", task);
  }

}
