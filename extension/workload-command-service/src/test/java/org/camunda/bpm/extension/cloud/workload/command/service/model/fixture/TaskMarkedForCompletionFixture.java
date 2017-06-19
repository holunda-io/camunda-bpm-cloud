package org.camunda.bpm.extension.cloud.workload.command.service.model.fixture;

import org.camunda.bpm.extension.cloud.workload.event.TaskMarkedForCompletionEvent;

public class TaskMarkedForCompletionFixture {

  public static TaskMarkedForCompletionEvent fooTaskMarkedForCompletion() {
    return TaskMarkedForCompletionEvent.builder()
      .taskId("foo")
      .build();
  }
}
