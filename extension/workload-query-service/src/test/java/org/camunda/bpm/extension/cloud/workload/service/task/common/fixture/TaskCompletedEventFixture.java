package org.camunda.bpm.extension.cloud.workload.service.task.common.fixture;

import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCompletedEvent;

public class TaskCompletedEventFixture {

  public static TaskCompletedEvent fooTaskCompletedEvent(){
    return TaskCompletedEvent.builder()
      .taskId("foo")
      .build();
  }
}
