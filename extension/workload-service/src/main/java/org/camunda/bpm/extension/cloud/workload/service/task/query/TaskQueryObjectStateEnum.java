package org.camunda.bpm.extension.cloud.workload.service.task.query;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TaskQueryObjectStateEnum {

  CREATED, PENDING_TO_COMPLETE, COMPLETED_SENT, COMPLETED;

  private TaskQueryObjectStateEnum state;

  TaskQueryObjectStateEnum()
  {
    state = this;
  }

  public TaskQueryObjectStateEnum next(){

    switch (state)
    {
      case CREATED:
        return PENDING_TO_COMPLETE;
      case PENDING_TO_COMPLETE:
        return COMPLETED_SENT;
      case COMPLETED_SENT:
        return COMPLETED;
    }

    return COMPLETED;
  }

}
