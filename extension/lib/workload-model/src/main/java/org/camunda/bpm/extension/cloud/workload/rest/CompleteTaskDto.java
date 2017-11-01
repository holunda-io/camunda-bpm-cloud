package org.camunda.bpm.extension.cloud.workload.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteTaskDto {

  @NonNull
  @TargetAggregateIdentifier
  private String taskId;

  private String assignee;

  @Singular
  private Map<String, Object> variables;

}
