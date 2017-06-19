package org.camunda.bpm.extension.cloud.workload.command;


import java.io.Serializable;

public interface TaskCommand extends Serializable {

  String getTaskId();

}
