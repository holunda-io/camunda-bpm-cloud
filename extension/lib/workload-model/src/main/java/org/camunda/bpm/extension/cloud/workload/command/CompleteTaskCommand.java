package org.camunda.bpm.extension.cloud.workload.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteTaskCommand implements TaskCommand {

  @NonNull
  @TargetAggregateIdentifier
  private String taskId;

  private String assignee;
  private String caseDefinitionId;
  private String caseExecutionId;
  private Date createTime;
  private String description;
  private Date dueDate;
  private String formKey;
  private String engineId;
  private String eventType;
  private String name;
  private String owner;
  private int priority;
  private String processInstanceId;
  private String tenantId;
  private String taskDefinitionKey;
}
