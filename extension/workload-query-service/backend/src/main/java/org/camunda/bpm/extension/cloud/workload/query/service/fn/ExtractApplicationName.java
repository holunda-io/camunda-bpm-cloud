package org.camunda.bpm.extension.cloud.workload.query.service.fn;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.UnaryOperator;

/**
 * Extract application name from given taskId.
 */
@Component
public class ExtractApplicationName implements UnaryOperator<String> {


  @Override
  public String apply(final String taskId) {
    final String[] parts = taskId.split("-");

    return String.join("-", Arrays.copyOfRange(parts, 0, parts.length - 5));
  }
}
