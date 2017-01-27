package org.camunda.bpm.extension.cloud.event.service.rest;

import lombok.Builder;
import lombok.Data;
import org.camunda.bpm.extension.cloud.event.service.acceptor.TaskEvent;
import org.camunda.bpm.extension.cloud.event.service.domain.TaskCreatedEvent;

@Data
@Builder
public class Task {

  private String assignee;
  private String caseDefinitionId;
  private String caseExecutionId;
  private String createTime;
  private String description;
  private String dueDate;
  private String formKey;
  private String engineId;
  private TaskStateEnum eventType;
  private String name;
  private String owner;
  private String priority;
  private String processInstanceId;
  private String tenantId;
  private String taskDefinitionKey;
  private String taskId;

  public static Task from(TaskEvent taskEvent){
    return Task.builder()
      .assignee(taskEvent.getAssignee())
      .caseDefinitionId(taskEvent.getCaseDefinitionId())
      .caseExecutionId(taskEvent.getCaseExecutionId())
      .createTime(taskEvent.getCreateTime())
      .description(taskEvent.getDescription())
      .dueDate(taskEvent.getDueDate())
      .formKey(taskEvent.getFormKey())
      .engineId(taskEvent.getEngineId())
      .eventType(TaskStateEnum.valueOf(taskEvent.getEventType()))
      .name(taskEvent.getName())
      .owner(taskEvent.getOwner())
      .priority(taskEvent.getPriority())
      .processInstanceId(taskEvent.getProcessInstanceId())
      .tenantId(taskEvent.getTenantId())
      .taskDefinitionKey(taskEvent.getTaskDefinitionKey())
      .taskId(taskEvent.getTaskId())
      .build();
  }

  public static Task from(TaskCreatedEvent taskEvent){
    return Task.builder()
      .assignee(taskEvent.getAssignee())
      .caseDefinitionId(taskEvent.getCaseDefinitionId())
      .caseExecutionId(taskEvent.getCaseExecutionId())
      .createTime(taskEvent.getCreateTime())
      .description(taskEvent.getDescription())
      .dueDate(taskEvent.getDueDate())
      .formKey(taskEvent.getFormKey())
      .engineId(taskEvent.getEngineId())
      .eventType(TaskStateEnum.valueOf(taskEvent.getEventType()))
      .name(taskEvent.getName())
      .owner(taskEvent.getOwner())
      .priority(taskEvent.getPriority())
      .processInstanceId(taskEvent.getProcessInstanceId())
      .tenantId(taskEvent.getTenantId())
      .taskDefinitionKey(taskEvent.getTaskDefinitionKey())
      .taskId(taskEvent.getTaskId())
      .build();
  }

}
