package org.camunda.bpm.extension.cloud.workload.service.task.common;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskAggregate;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.MarkTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskMarkedForCompletionFixture;
import org.junit.Before;
import org.junit.Test;

public class TaskMarkedForCompletionEventTest {

  FixtureConfiguration fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture(TaskAggregate.class);
  }

  @Test
  public void fooEventIsFired() {
    String taskId = "foo";
    TaskCreatedEvent taskCreatedEvent = TaskCreatedEventFixture.fooTaskCreatedEvent();
    MarkTaskForCompletionCommand markTaskForCompletionCommand = markTaskForCompletionCommandWithTaskId("foo");
    TaskMarkedForCompletionEvent taskMarkedForCompletionEvent = TaskMarkedForCompletionFixture.fooTaskMarkedForCompletion();

    fixture.given(taskCreatedEvent)
      .when(markTaskForCompletionCommand)
      .expectSuccessfulHandlerExecution()
      .expectEvents(taskMarkedForCompletionEvent);
  }

  private MarkTaskForCompletionCommand markTaskForCompletionCommandWithTaskId(String taskId) {
    MarkTaskForCompletionCommand markTaskForCompletionCommand = new MarkTaskForCompletionCommand();
    markTaskForCompletionCommand.setTaskId(taskId);
    return markTaskForCompletionCommand;
  }

}
