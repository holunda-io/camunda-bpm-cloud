package org.camunda.bpm.extension.cloud.workload.service.task.query;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskQueryObjectStateEnumTest {
  @Test
  public void next() throws Exception {
    TaskQueryObjectStateEnum stateEnum = TaskQueryObjectStateEnum.CREATED.next();
    assertThat(stateEnum).isEqualTo(TaskQueryObjectStateEnum.PENDING_TO_COMPLETE);

    stateEnum = stateEnum.next();
    assertThat(stateEnum).isEqualTo(TaskQueryObjectStateEnum.COMPLETED_SENT);

    stateEnum = stateEnum.next();
    assertThat(stateEnum).isEqualTo(TaskQueryObjectStateEnum.COMPLETED);

    stateEnum = stateEnum.next();
    assertThat(stateEnum).isEqualTo(TaskQueryObjectStateEnum.COMPLETED);
  }

}
