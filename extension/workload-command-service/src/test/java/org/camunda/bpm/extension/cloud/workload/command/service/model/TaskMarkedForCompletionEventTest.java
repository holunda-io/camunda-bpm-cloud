package org.camunda.bpm.extension.cloud.workload.command.service.model;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.command.MarkTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskMarkedForCompletionFixture;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskMarkedForCompletionEvent;
import org.junit.Test;

public class TaskMarkedForCompletionEventTest {

  private final FixtureConfiguration<TaskAggregate> fixture = new AggregateTestFixture<>(TaskAggregate.class);

  @Test
  public void fooEventIsFired() {
    String taskId = "foo";
    TaskCreatedEvent taskCreatedEvent = TaskCreatedEventFixture.fooTaskCreatedEvent();
    MarkTaskForCompletionCommand markTaskForCompletionCommand = markTaskForCompletionCommandWithTaskId(taskId);
    TaskMarkedForCompletionEvent taskMarkedForCompletionEvent = TaskMarkedForCompletionFixture
      .fooTaskMarkedForCompletion();

    fixture.given(taskCreatedEvent).when(markTaskForCompletionCommand).expectSuccessfulHandlerExecution()
      .expectEvents(taskMarkedForCompletionEvent);
  }

  private MarkTaskForCompletionCommand markTaskForCompletionCommandWithTaskId(String taskId) {
    MarkTaskForCompletionCommand markTaskForCompletionCommand = new MarkTaskForCompletionCommand();
    markTaskForCompletionCommand.setTaskId(taskId);
    return markTaskForCompletionCommand;
  }

}
