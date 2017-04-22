package org.camunda.bpm.extension.cloud.workload.service.task.event.fixture;

import org.camunda.bpm.extension.cloud.workload.service.task.event.TaskCreatedEvent;

public class TaskCreatedEventFixture {

  public static TaskCreatedEvent fooTaskCreatedEvent(){
    return TaskCreatedEvent.builder()
      .taskId("foo")
      .build();
  }

}
