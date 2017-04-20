package org.camunda.bpm.extension.cloud.workload.service.task.common;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskAggregate;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskCompletedEventFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskMarkedForCompletionFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskSentToBeCompletedEventFixture;
import org.junit.Before;
import org.junit.Test;

public class TaskCompletedEventTest {

  FixtureConfiguration<TaskAggregate> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<TaskAggregate>(TaskAggregate.class);
  }

  @Test
  public void fooEventIsFired() {
    TaskCreatedEvent taskCreatedEvent = TaskCreatedEventFixture.fooTaskCreatedEvent();
    TaskMarkedForCompletionEvent taskMarkedForCompletionEvent = TaskMarkedForCompletionFixture.fooTaskMarkedForCompletion();
    TaskSentToBeCompletedEvent taskSentToBeCompletedEvent = TaskSentToBeCompletedEventFixture.fooTaskSentToBeCompletedEvent();
    CompleteTaskCommand completeTaskCommand = completeTaskCommandWithTaskId("foo");
    TaskCompletedEvent taskCompletedEvent = TaskCompletedEventFixture.fooTaskCompletedEvent();

    fixture.given(taskCreatedEvent, taskMarkedForCompletionEvent, taskSentToBeCompletedEvent)
      .when(completeTaskCommand)
      .expectSuccessfulHandlerExecution()
      .expectEvents(taskCompletedEvent);
  }

  private CompleteTaskCommand completeTaskCommandWithTaskId(String taskId) {
    CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand();
    completeTaskCommand.setTaskId(taskId);
    return completeTaskCommand;
  }
}
