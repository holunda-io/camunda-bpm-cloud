package org.camunda.bpm.extension.cloud.workload.service.task.query.fixture;

import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObjectStateEnum;

public class TaskQueryObjectFixture {

  public static TaskQueryObject fooTaskQueryObject(){
    return TaskQueryObject.builder()
      .taskId("foo")
      .build();
  }

  public static TaskQueryObject fooTaskQueryObjectCreated(){
    return TaskQueryObject.builder()
      .taskId("foo")
      .eventType(TaskQueryObjectStateEnum.CREATED)
      .build();
  }

  public static TaskQueryObject fooTaskQueryObjectPendingToComplete(){
    return TaskQueryObject.builder()
      .taskId("foo")
      .eventType(TaskQueryObjectStateEnum.PENDING_TO_COMPLETE)
      .build();
  }

  public static TaskQueryObject fooTaskQueryObjectCompletedSent(){
    return TaskQueryObject.builder()
      .taskId("foo")
      .eventType(TaskQueryObjectStateEnum.COMPLETED_SENT)
      .build();
  }

}
