package org.camunda.bpm.extension.cloud.workload.query.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.camunda.bpm.extension.cloud.workload.event.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.event.TaskCreatedEvent;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.net.URL;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskQueryObject {

  @Id
  private String taskId;

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

  @Transient
  private URL taskFormUrl;


  public static TaskQueryObject from(TaskCreatedEvent taskEvent) {
    return TaskQueryObject.builder()
      .assignee(taskEvent.getAssignee())
      .caseDefinitionId(taskEvent.getCaseDefinitionId())
      .caseExecutionId(taskEvent.getCaseExecutionId())
      .createTime(taskEvent.getCreateTime())
      .description(taskEvent.getDescription())
      .dueDate(taskEvent.getDueDate())
      .formKey(taskEvent.getFormKey())
      .engineId(taskEvent.getEngineId())
      .eventType(TaskQueryObjectStateEnum.CREATED)
      .name(taskEvent.getName())
      .owner(taskEvent.getOwner())
      .priority(taskEvent.getPriority())
      .processInstanceId(taskEvent.getProcessInstanceId())
      .tenantId(taskEvent.getTenantId())
      .taskDefinitionKey(taskEvent.getTaskDefinitionKey())
      .taskId(taskEvent.getTaskId())
      .build();
  }

  public static TaskQueryObject from(TaskCompletedEvent taskEvent) {
    return TaskQueryObject.builder()
      .assignee(taskEvent.getAssignee())
      .caseDefinitionId(taskEvent.getCaseDefinitionId())
      .caseExecutionId(taskEvent.getCaseExecutionId())
      .createTime(taskEvent.getCreateTime())
      .description(taskEvent.getDescription())
      .dueDate(taskEvent.getDueDate())
      .formKey(taskEvent.getFormKey())
      .engineId(taskEvent.getEngineId())
      .eventType(TaskQueryObjectStateEnum.COMPLETED)
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
