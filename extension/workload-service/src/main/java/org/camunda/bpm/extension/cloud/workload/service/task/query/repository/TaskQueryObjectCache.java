package org.camunda.bpm.extension.cloud.workload.service.task.query.repository;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObjectStateEnum;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TaskQueryObjectCache {

  private HashMap<String, TaskQueryObject> eventCash;

  public TaskQueryObjectCache() {
    this.eventCash = new HashMap<>();
  }

  public TaskQueryObjectCache(HashMap<String, TaskQueryObject> eventCash) {
    this.eventCash = eventCash;
  }

  public void putEvent(TaskQueryObject taskQueryObject) {
    eventCash.put(taskQueryObject.getTaskId(), taskQueryObject);
    log.info("TaskQueryObject cached: {}", taskQueryObject);
  }

  public TaskQueryObject getEvent(String key) {
    return eventCash.get(key);
  }

  public Collection<TaskQueryObject> getEvents() {
    return this.eventCash.values();
  }

  public Collection<TaskQueryObject> getEvents(TaskQueryObjectStateEnum status) {
    return this.eventCash.values().stream().filter(e -> e.getEventType().equals(status)).collect(Collectors.toList());
  }

  public void removeEvent(TaskQueryObject taskQueryObject) {
    this.eventCash.remove(taskQueryObject.getTaskId());
    log.info("TaskQueryObject removed from eventCache: {}", taskQueryObject);
  }

}
