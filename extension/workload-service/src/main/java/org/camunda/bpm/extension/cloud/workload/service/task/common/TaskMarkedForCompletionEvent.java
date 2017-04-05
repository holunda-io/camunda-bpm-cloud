package org.camunda.bpm.extension.cloud.workload.service.task.common;

import lombok.Data;

@Data
public class TaskMarkedForCompletionEvent {

  private String assignee;
  private String caseDefinitionId;
  private String caseExecutionId;
  private String createTime;
  private String description;
  private String dueDate;
  private String formKey;
  private String engineId;
  private String eventType;
  private String name;
  private String owner;
  private String priority;
  private String processInstanceId;
  private String tenantId;
  private String taskDefinitionKey;
  private String taskId;

}