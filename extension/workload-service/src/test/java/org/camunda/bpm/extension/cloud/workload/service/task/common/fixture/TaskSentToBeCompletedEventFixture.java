package org.camunda.bpm.extension.cloud.workload.service.task.common.fixture;


import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskSentToBeCompletedEvent;

public class TaskSentToBeCompletedEventFixture {

  public static TaskSentToBeCompletedEvent fooTaskSentToBeCompletedEvent() {
    return TaskSentToBeCompletedEvent.builder()
      .taskId("foo")
      .build();
  }
}
