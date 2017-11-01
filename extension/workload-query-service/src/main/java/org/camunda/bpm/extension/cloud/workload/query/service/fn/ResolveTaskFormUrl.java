package org.camunda.bpm.extension.cloud.workload.query.service.fn;

import lombok.SneakyThrows;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.function.UnaryOperator;

/**
 * Takes the tasks form key and creates a valid pcf routing url based on the application name.
 */
@Component
public class ResolveTaskFormUrl implements UnaryOperator<TaskQueryObject> {

  private final ExtractApplicationName extractApplicationName;
  private final String taskUrlTemplate;

  public ResolveTaskFormUrl(final ExtractApplicationName extractApplicationName,
                            @Value("${camunda.bpm.cloud.taskUrlTemplate}") String taskUrlTemplate) {
    this.extractApplicationName = extractApplicationName;
    this.taskUrlTemplate = taskUrlTemplate;
  }

  @Override
  @SneakyThrows
  public TaskQueryObject apply(final TaskQueryObject task) {
    String applicationName = extractApplicationName.apply(task.getTaskId());

    task.setTaskFormUrl(new URL(String.format(taskUrlTemplate, applicationName, task.getFormKey(), task.getTaskId())));

    return task;
  }
}
