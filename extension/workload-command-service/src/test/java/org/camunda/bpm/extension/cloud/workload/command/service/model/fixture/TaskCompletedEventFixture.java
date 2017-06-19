package org.camunda.bpm.extension.cloud.workload.command.service.model.fixture;

import org.camunda.bpm.extension.cloud.workload.event.TaskCompletedEvent;

public class TaskCompletedEventFixture {

  public static TaskCompletedEvent fooTaskCompletedEvent() {
    return TaskCompletedEvent.builder()
      .taskId("foo")
      .build();
  }
}
