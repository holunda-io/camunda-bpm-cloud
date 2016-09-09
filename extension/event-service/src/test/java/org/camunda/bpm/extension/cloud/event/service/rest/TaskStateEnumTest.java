package org.camunda.bpm.extension.cloud.event.service.rest;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TaskStateEnumTest {

  @Test
  public void testNext() throws Exception {
    TaskStateEnum stateEnum = TaskStateEnum.CREATED.next();
    Assertions.assertThat(stateEnum).isEqualTo(TaskStateEnum.PENDING_TO_COMPLETE);

    stateEnum = stateEnum.next();
    Assertions.assertThat(stateEnum).isEqualTo(TaskStateEnum.COMPLETED_SENT);

    stateEnum = stateEnum.next();
    Assertions.assertThat(stateEnum).isEqualTo(TaskStateEnum.COMPLETED);
  }
}
