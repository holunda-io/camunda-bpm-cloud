package org.camunda.bpm.extension.cloud.workload.query.service.controller;

import org.camunda.bpm.extension.cloud.workload.query.service.fn.ResolveTaskFormUrl;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObjectRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskQueryController {

  private final TaskQueryObjectRepository repository;
  private final ResolveTaskFormUrl resolveTaskFormUrl;

  public TaskQueryController(final TaskQueryObjectRepository repository, final ResolveTaskFormUrl resolveTaskFormUrl) {
    this.repository = repository;
    this.resolveTaskFormUrl = resolveTaskFormUrl;
  }

  @GetMapping(path = "/tasks", produces = "application/json")
  public List<TaskQueryObject> getAllTasks() {
    return this.repository.findAll().stream()
      .map(this.resolveTaskFormUrl)
      .collect(Collectors.toList());
  }

}
