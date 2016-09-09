package org.camunda.bpm.extension.cloud.event.service.rest;

import lombok.Data;
import lombok.ToString;

@Data
public class TaskEvent {

  private String assignee;
  private String caseDefinitionId;
  private String caseExecutionId;
  private String createTime;
  private String description;
  private String dueDate;
  private String formKey;
  private String engineId;
  private String eventType;
  private String owner;
  private String priority;
  private String processInstanceId;
  private String tenantId;
  private String taskDefinitionKey;
  private String taskId;

}
