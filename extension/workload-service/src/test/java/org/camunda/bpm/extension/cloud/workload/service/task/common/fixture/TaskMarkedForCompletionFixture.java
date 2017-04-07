package org.camunda.bpm.extension.cloud.workload.service.task.common.fixture;

import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskMarkedForCompletionEvent;

public class TaskMarkedForCompletionFixture {

  public static TaskMarkedForCompletionEvent fooTaskMarkedForCompletion(){
    return TaskMarkedForCompletionEvent.builder()
      .taskId("foo")
      .build();
  }
}
