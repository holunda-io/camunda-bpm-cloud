package org.camunda.bpm.extension.cloud.workload.service.task.event.fixture;


import org.camunda.bpm.extension.cloud.workload.service.task.event.TaskSentToBeCompletedEvent;

public class TaskSentToBeCompletedEventFixture {

  public static TaskSentToBeCompletedEvent fooTaskSentToBeCompletedEvent() {
    return TaskSentToBeCompletedEvent.builder()
      .taskId("foo")
      .build();
  }
}
