package org.camunda.bpm.extension.cloud.workload.command.service.model;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskCompletedEventFixture;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskMarkedForCompletionFixture;
import org.camunda.bpm.extension.cloud.workload.event.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskMarkedForCompletionEvent;
import org.junit.Test;

public class TaskCompletedEventTest {

  private final FixtureConfiguration<TaskAggregate> fixture = new AggregateTestFixture<>(TaskAggregate.class);

  @Test
  public void fooEventIsFired() {
    final TaskCreatedEvent taskCreatedEvent = TaskCreatedEventFixture.fooTaskCreatedEvent();
    final TaskMarkedForCompletionEvent taskMarkedForCompletionEvent = TaskMarkedForCompletionFixture.fooTaskMarkedForCompletion();
    final CompleteTaskCommand completeTaskCommand = completeTaskCommandWithTaskId("foo");
    final TaskCompletedEvent taskCompletedEvent = TaskCompletedEventFixture.fooTaskCompletedEvent();

    this.fixture.given(taskCreatedEvent, taskMarkedForCompletionEvent)
      .when(completeTaskCommand)
      .expectSuccessfulHandlerExecution()
      .expectEvents(taskCompletedEvent);
  }

  private CompleteTaskCommand completeTaskCommandWithTaskId(final String taskId) {
    final CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand();
    completeTaskCommand.setTaskId(taskId);
    return completeTaskCommand;
  }
}
