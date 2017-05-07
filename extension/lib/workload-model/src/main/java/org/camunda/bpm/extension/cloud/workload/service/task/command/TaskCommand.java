package org.camunda.bpm.extension.cloud.workload.service.task.command;


import java.io.Serializable;

public interface TaskCommand extends Serializable {

  String getTaskId();

}
