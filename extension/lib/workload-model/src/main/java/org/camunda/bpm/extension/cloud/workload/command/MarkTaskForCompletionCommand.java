package org.camunda.bpm.extension.cloud.workload.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkTaskForCompletionCommand implements TaskCommand {

  @TargetAggregateIdentifier
  private String taskId;
}
