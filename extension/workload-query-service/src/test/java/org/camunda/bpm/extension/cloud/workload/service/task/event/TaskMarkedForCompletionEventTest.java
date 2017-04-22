package org.camunda.bpm.extension.cloud.workload.service.task.event;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.service.task.command.MarkTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskAggregate;
import org.camunda.bpm.extension.cloud.workload.service.task.event.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.event.fixture.TaskMarkedForCompletionFixture;
import org.junit.Before;
import org.junit.Test;

public class TaskMarkedForCompletionEventTest {

  FixtureConfiguration<TaskAggregate> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<TaskAggregate>(TaskAggregate.class);
  }

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
