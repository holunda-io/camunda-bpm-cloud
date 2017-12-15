package org.camunda.bpm.extension.cloud.workload.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskMarkedForCompletionEvent implements TaskEvent {

  private String taskId;

}
