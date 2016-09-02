package org.camunda.bpm.extension.cloud.event.service;

import lombok.Data;

@Data
public class TaskEvent {

  String eventType;
  String taskDefinitionKey;
  String taskId;
  String formKey;
  String engineId;

}
