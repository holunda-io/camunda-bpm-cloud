package org.camunda.bpm.extension.cloud.workload.service.task.common.fixture;

import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCreatedEvent;

public class TaskCreatedEventFixture {

  public static TaskCreatedEvent fooTaskCreatedEvent(){
    return TaskCreatedEvent.builder()
      .taskId("foo")
      .build();
  }

}
