package org.camunda.bpm.extension.cloud.workload.query.service.controller;

import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObjectRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskQueryController {

  private final TaskQueryObjectRepository repository;

  public TaskQueryController(TaskQueryObjectRepository repository) {
    this.repository = repository;
  }

  @GetMapping(path = "/tasks", produces = "application/json")
  public List<TaskQueryObject> getAllTasks() {
    return repository.findAll();
  }

}
