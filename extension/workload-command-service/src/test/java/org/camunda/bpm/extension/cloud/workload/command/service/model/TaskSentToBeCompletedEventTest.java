package org.camunda.bpm.extension.cloud.workload.command.service.model;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.command.SendTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskMarkedForCompletionFixture;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskSentToBeCompletedEventFixture;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskMarkedForCompletionEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskSentToBeCompletedEvent;
import org.junit.Test;

public class TaskSentToBeCompletedEventTest {
  private final FixtureConfiguration<TaskAggregate> fixture = new AggregateTestFixture<>(TaskAggregate.class);

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
