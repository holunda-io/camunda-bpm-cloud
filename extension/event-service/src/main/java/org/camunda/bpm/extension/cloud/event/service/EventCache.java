package org.camunda.bpm.extension.cloud.event.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Component
public class EventCache {

  public static final Logger LOGGER = LoggerFactory.getLogger(EventCache.class);

  private HashMap<String, TaskEvent> eventCash;

  public EventCache() {
    this.eventCash = new HashMap<String, TaskEvent>();
  }

  public EventCache(HashMap<String, TaskEvent> eventCash) {
    this.eventCash = eventCash;
  }

  public void putEvent(TaskEvent task){
    LOGGER.info("Event cached: {}", task);
    eventCash.put(generateKey(task), task);
  }

  public TaskEvent getEvent(String key) {
    return eventCash.get(key);
  }

  public Collection<TaskEvent> getEvents(){
    return this.eventCash.values();
  }

  public void removeEvent(TaskEvent task){
    LOGGER.info("Event removed from cache: {}", task);
    this.eventCash.remove(generateKey(task));
  }

  public String generateKey(TaskEvent taskEvent){
    return String.format("%s:%s:%s", taskEvent.getEngineId(), taskEvent.getTaskDefinitionKey(), taskEvent.getTaskId());
  }
}
