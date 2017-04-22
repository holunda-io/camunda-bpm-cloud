package org.camunda.bpm.extension.cloud.workload.service.task.event.fixture;

import org.camunda.bpm.extension.cloud.workload.service.task.event.TaskCompletedEvent;

public class TaskCompletedEventFixture {

  public static TaskCompletedEvent fooTaskCompletedEvent(){
    return TaskCompletedEvent.builder()
      .taskId("foo")
      .build();
  }
}
