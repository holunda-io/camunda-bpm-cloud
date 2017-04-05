import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskAggregate;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCreatedEvent;
import org.junit.Before;
import org.junit.Test;

public class CompleteTaskTest {

  private FixtureConfiguration fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture(TaskAggregate.class);
  }

  @Test
  public void fooEventIsFired() {
    TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent();
    taskCreatedEvent.setTaskId("foo");

    CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand();
    completeTaskCommand.setTaskId("foo");
    TaskCompletedEvent taskCompletedEvent = new TaskCompletedEvent();
    taskCompletedEvent.setTaskId("foo");
    fixture.given(taskCreatedEvent)
      .when(completeTaskCommand)
      .expectSuccessfulHandlerExecution()
      .expectEvents(taskCompletedEvent);
  }
}
