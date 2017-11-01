package org.camunda.bpm.extension.cloud.workload.command;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class CompleteTaskCommandTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void fluent_add_variable() throws Exception {
    CompleteTaskCommand command = CompleteTaskCommand.builder().taskId("1").variable("foo", 1L).build();

    assertThat(command.getVariables().get("foo")).isEqualTo(1L);
  }

  @Test
  public void fails_without_taskId() throws Exception {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("taskId");
    CompleteTaskCommand.builder().build();
  }

  @Test
  public void variables_empty_byDefault() throws Exception {
    assertThat(CompleteTaskCommand.builder().taskId("1").build().getVariables()).isEmpty();
  }
}
