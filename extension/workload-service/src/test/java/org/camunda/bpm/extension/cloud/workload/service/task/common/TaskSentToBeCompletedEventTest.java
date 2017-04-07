package org.camunda.bpm.extension.cloud.workload.service.task.common;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskAggregate;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.SendTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskMarkedForCompletionFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskSentToBeCompletedEventFixture;
import org.junit.Before;
import org.junit.Test;

public class TaskSentToBeCompletedEventTest {
  FixtureConfiguration fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture(TaskAggregate.class);
  }

  @Test
  public void fooEventIsFired() {
    TaskCreatedEvent taskCreatedEvent = TaskCreatedEventFixture.fooTaskCreatedEvent();
    TaskMarkedForCompletionEvent taskMarkedForCompletionEvent = TaskMarkedForCompletionFixture.fooTaskMarkedForCompletion();
    SendTaskForCompletionCommand sendTaskForCompletionCommand = sendTaskForCompletionCommandWithTaskId("foo");
    TaskSentToBeCompletedEvent taskSentToBeCompletedEvent = TaskSentToBeCompletedEventFixture.fooTaskSentToBeCompletedEvent();

    fixture.given(taskCreatedEvent, taskMarkedForCompletionEvent)
      .when(sendTaskForCompletionCommand)
      .expectSuccessfulHandlerExecution()
      .expectEvents(taskSentToBeCompletedEvent);
  }

  private SendTaskForCompletionCommand sendTaskForCompletionCommandWithTaskId(String taskId) {
    SendTaskForCompletionCommand sendTaskForCompletionCommand = new SendTaskForCompletionCommand();
    sendTaskForCompletionCommand.setTaskId(taskId);
    return sendTaskForCompletionCommand;
  }

}
