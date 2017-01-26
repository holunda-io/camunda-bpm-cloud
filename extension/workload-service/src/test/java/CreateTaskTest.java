import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.camunda.bpm.extension.cloud.event.service.acceptor.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.event.service.domain.TaskAggregate;
import org.camunda.bpm.extension.cloud.event.service.domain.TaskCreatedEvent;
import org.junit.Before;
import org.junit.Test;

public class CreateTaskTest {

  private FixtureConfiguration fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture(TaskAggregate.class);
  }

  @Test
  public void fooEventIsFired() {
    CreateTaskCommand createTaskCommand = new CreateTaskCommand();
    createTaskCommand.setTaskId("foo");
    TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent();
    taskCreatedEvent.setTaskId("foo");
    fixture.givenNoPriorActivity()
      .when(createTaskCommand)
      .expectSuccessfulHandlerExecution()
      .expectEvents(taskCreatedEvent);
  }

}
