package org.camunda.bpm.extension.cloud.event.service;

public class TaskEvent {

  private String eventType;
  private String taskDefinitionKey;
  private String taskId;
  private String formKey;
  private String engineId;

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getEngineId() {
    return engineId;
  }

  public void setEngineId(String engineId) {
    this.engineId = engineId;
  }

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
    return "TaskEvent [eventType=" + eventType + ", taskDefinitionKey=" + taskDefinitionKey + ", taskId=" + taskId
        + ", formKey=" + formKey + ", engineUrl=" + engineId + "]";
  }

}
