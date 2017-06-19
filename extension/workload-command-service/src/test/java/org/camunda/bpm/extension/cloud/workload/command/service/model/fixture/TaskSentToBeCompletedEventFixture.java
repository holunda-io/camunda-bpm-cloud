package org.camunda.bpm.extension.cloud.workload.command.service.model.fixture;

import org.camunda.bpm.extension.cloud.workload.event.TaskSentToBeCompletedEvent;

public class TaskSentToBeCompletedEventFixture {

  public static TaskSentToBeCompletedEvent fooTaskSentToBeCompletedEvent() {
    return TaskSentToBeCompletedEvent.builder()
      .taskId("foo")
      .build();
  }
}
