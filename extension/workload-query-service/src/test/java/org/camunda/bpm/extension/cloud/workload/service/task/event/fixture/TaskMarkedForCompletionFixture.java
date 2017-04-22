package org.camunda.bpm.extension.cloud.workload.service.task.event.fixture;

import org.camunda.bpm.extension.cloud.workload.service.task.event.TaskMarkedForCompletionEvent;

public class TaskMarkedForCompletionFixture {

  public static TaskMarkedForCompletionEvent fooTaskMarkedForCompletion(){
    return TaskMarkedForCompletionEvent.builder()
      .taskId("foo")
      .build();
  }
}
