package org.camunda.bpm.extension.cloud.event.service;

import java.util.Collection;
import java.util.HashMap;

import org.camunda.bpm.extension.cloud.event.service.rest.TaskEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

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
    log.info("Event cached: {}", task);
    eventCash.put(generateKey(task), task);
  }

  public TaskEvent getEvent(String key) {
    return eventCash.get(key);
  }

  public Collection<TaskEvent> getEvents() {
    return this.eventCash.values();
  }

  public void removeEvent(TaskEvent task) {
    log.info("Event removed from cache: {}", task);
    this.eventCash.remove(generateKey(task));
  }

  public String generateKey(TaskEvent taskEvent) {
    return String.format("%s:%s:%s", taskEvent.getEngineId(), taskEvent.getTaskDefinitionKey(), taskEvent.getTaskId());
  }
}
