package org.camunda.bpm.extension.cloud.workload.command.service.model;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskCompletedEventFixture;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskMarkedForCompletionFixture;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskSentToBeCompletedEventFixture;
import org.camunda.bpm.extension.cloud.workload.event.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskMarkedForCompletionEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskSentToBeCompletedEvent;
import org.junit.Test;

public class TaskCompletedEventTest {

  private final FixtureConfiguration<TaskAggregate> fixture = new AggregateTestFixture<>(TaskAggregate.class);

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
