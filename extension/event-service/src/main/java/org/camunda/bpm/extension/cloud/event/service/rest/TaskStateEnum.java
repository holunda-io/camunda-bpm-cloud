package org.camunda.bpm.extension.cloud.event.service.rest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TaskStateEnum {

  CREATED, PENDING_TO_COMPLETE, COMPLETED_SENT, COMPLETED;

  private TaskStateEnum state;

  private TaskStateEnum()
  {
    state = this;
  }

  public TaskStateEnum next(){

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
