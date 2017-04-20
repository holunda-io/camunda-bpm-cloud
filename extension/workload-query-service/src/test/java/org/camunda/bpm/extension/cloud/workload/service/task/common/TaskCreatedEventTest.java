package org.camunda.bpm.extension.cloud.workload.service.task.common;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskAggregate;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskCreatedEventFixture;
import org.junit.Before;
import org.junit.Test;

public class TaskCreatedEventTest {

  FixtureConfiguration<TaskAggregate> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<TaskAggregate>(TaskAggregate.class);
  }

  @Test
  public void fooEventIsFired() {
    CreateTaskCommand createTaskCommand = createTaskCommandWithTaskId("foo");
    TaskCreatedEvent taskCreatedEvent = TaskCreatedEventFixture.fooTaskCreatedEvent();

    fixture.givenNoPriorActivity()
      .when(createTaskCommand)
      .expectSuccessfulHandlerExecution()
      .expectEvents(taskCreatedEvent);
  }

  private CreateTaskCommand createTaskCommandWithTaskId(String taskId) {
    CreateTaskCommand createTaskCommand = new CreateTaskCommand();
    createTaskCommand.setTaskId(taskId);
    return createTaskCommand;
  }

}
