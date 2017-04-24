package org.camunda.bpm.extension.cloud.workload.service.task.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendTaskForCompletionCommand implements TaskCommand {

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

  @TargetAggregateIdentifier
  private String taskId;
}
