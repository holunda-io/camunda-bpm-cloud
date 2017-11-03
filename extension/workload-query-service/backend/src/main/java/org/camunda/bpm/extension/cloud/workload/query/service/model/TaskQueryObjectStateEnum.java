package org.camunda.bpm.extension.cloud.workload.query.service.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TaskQueryObjectStateEnum {

  CREATED {
    @Override
    public TaskQueryObjectStateEnum next() {
      return PENDING_TO_COMPLETE;
    }
  },
  PENDING_TO_COMPLETE {
    @Override
    public TaskQueryObjectStateEnum next() {
      return COMPLETED_SENT;
    }
  },
  COMPLETED_SENT {
    @Override
    public TaskQueryObjectStateEnum next() {
      return COMPLETED;
    }
  },
  COMPLETED {
    @Override
    public TaskQueryObjectStateEnum next() {
      return COMPLETED;
    }
  };

  public abstract TaskQueryObjectStateEnum next();
}
