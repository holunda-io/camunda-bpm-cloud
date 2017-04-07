package org.camunda.bpm.extension.cloud.workload.service.task.query;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TaskQueryObjectStateEnum {

  CREATED {
    public TaskQueryObjectStateEnum next() {
      return PENDING_TO_COMPLETE;
    }
  },
  PENDING_TO_COMPLETE {
    public TaskQueryObjectStateEnum next() {
      return COMPLETED_SENT;
    }
  },
  COMPLETED_SENT {
    public TaskQueryObjectStateEnum next() {
      return COMPLETED;
    }
  },
  COMPLETED {
    public TaskQueryObjectStateEnum next() {
      return COMPLETED;
    }
  };

  public abstract TaskQueryObjectStateEnum next();
}
