package org.camunda.bpm.extension.cloud.workload.command.service.model.fixture;

import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;

public class TaskCreatedEventFixture {

  public static TaskCreatedEvent fooTaskCreatedEvent() {
    return TaskCreatedEvent.builder()
      .taskId("foo")
      .build();
  }

}
