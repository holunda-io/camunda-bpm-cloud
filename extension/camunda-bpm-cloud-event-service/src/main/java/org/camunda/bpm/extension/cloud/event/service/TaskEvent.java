package org.camunda.bpm.extension.cloud.event.service;

public class TaskEvent {

  private String taskDefinitionKey;
  private String taskId;
  private String formKey;

  public String getTaskDefinitionKey() {
    return taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey) {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getFormKey() {
    return formKey;
  }

  public void setFormKey(String formKey) {
    this.formKey = formKey;
  }

  @Override
  public String toString() {
    return "TaskEvent [taskDefinitionKey=" + taskDefinitionKey + ", taskId=" + taskId + ", formKey=" + formKey + "]";
  }

}
