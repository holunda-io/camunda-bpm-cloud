package org.camunda.bpm.extension.cloud.event.service.rest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaskStateEnumTest {

  @Test
  public void testNext() throws Exception {
    TaskStateEnum stateEnum = TaskStateEnum.CREATED.next();
    assertEquals(stateEnum, TaskStateEnum.PENDING_TO_COMPLETE);

    stateEnum = stateEnum.next();
    assertEquals(stateEnum, TaskStateEnum.COMPLETED_SENT);

    stateEnum = stateEnum.next();
    assertEquals(stateEnum, TaskStateEnum.COMPLETED);
  }
}
