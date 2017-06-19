package org.camunda.bpm.extension.cloud.workload.command.service.model;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.service.model.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;
import org.junit.Test;

public class TaskCreatedEventTest {

  private final FixtureConfiguration<TaskAggregate> fixture = new AggregateTestFixture<>(TaskAggregate.class);

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
