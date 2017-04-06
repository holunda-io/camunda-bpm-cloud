package org.camunda.bpm.extension.cloud.workload.service.task.query;

import lombok.*;
import org.camunda.bpm.extension.cloud.workload.service.task.command.api.TaskCommandMessage;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCreatedEvent;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskQueryObject {

  private String assignee;
  private String caseDefinitionId;
  private String caseExecutionId;
  private String createTime;
  private String description;
  private String dueDate;
  private String formKey;
  private String engineId;
  @Enumerated(EnumType.STRING)
  private TaskQueryObjectStateEnum eventType;
  private String name;
  private String owner;
  private String priority;
  private String processInstanceId;
  private String tenantId;
  private String taskDefinitionKey;
  @Id
  private String taskId;

  public static TaskQueryObject from(TaskCommandMessage taskCommandMessage){
    return TaskQueryObject.builder()
      .assignee(taskCommandMessage.getAssignee())
      .caseDefinitionId(taskCommandMessage.getCaseDefinitionId())
      .caseExecutionId(taskCommandMessage.getCaseExecutionId())
      .createTime(taskCommandMessage.getCreateTime())
      .description(taskCommandMessage.getDescription())
      .dueDate(taskCommandMessage.getDueDate())
      .formKey(taskCommandMessage.getFormKey())
      .engineId(taskCommandMessage.getEngineId())
      .eventType(TaskQueryObjectStateEnum.valueOf(taskCommandMessage.getEventType()))
      .name(taskCommandMessage.getName())
      .owner(taskCommandMessage.getOwner())
      .priority(taskCommandMessage.getPriority())
      .processInstanceId(taskCommandMessage.getProcessInstanceId())
      .tenantId(taskCommandMessage.getTenantId())
      .taskDefinitionKey(taskCommandMessage.getTaskDefinitionKey())
      .taskId(taskCommandMessage.getTaskId())
      .build();
  }

  public static TaskQueryObject from(TaskCreatedEvent taskEvent){
    return TaskQueryObject.builder()
      .assignee(taskEvent.getAssignee())
      .caseDefinitionId(taskEvent.getCaseDefinitionId())
      .caseExecutionId(taskEvent.getCaseExecutionId())
      .createTime(taskEvent.getCreateTime())
      .description(taskEvent.getDescription())
      .dueDate(taskEvent.getDueDate())
      .formKey(taskEvent.getFormKey())
      .engineId(taskEvent.getEngineId())
      .eventType(TaskQueryObjectStateEnum.valueOf(taskEvent.getEventType()))
      .name(taskEvent.getName())
      .owner(taskEvent.getOwner())
      .priority(taskEvent.getPriority())
      .processInstanceId(taskEvent.getProcessInstanceId())
      .tenantId(taskEvent.getTenantId())
      .taskDefinitionKey(taskEvent.getTaskDefinitionKey())
      .taskId(taskEvent.getTaskId())
      .build();
  }

  public static TaskQueryObject from(TaskCompletedEvent taskEvent){
    return TaskQueryObject.builder()
      .assignee(taskEvent.getAssignee())
      .caseDefinitionId(taskEvent.getCaseDefinitionId())
      .caseExecutionId(taskEvent.getCaseExecutionId())
      .createTime(taskEvent.getCreateTime())
      .description(taskEvent.getDescription())
      .dueDate(taskEvent.getDueDate())
      .formKey(taskEvent.getFormKey())
      .engineId(taskEvent.getEngineId())
      .eventType(TaskQueryObjectStateEnum.valueOf(taskEvent.getEventType()))
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
