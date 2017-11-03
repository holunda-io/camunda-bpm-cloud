package org.camunda.bpm.extension.example.process.simple.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class SimpleTaskController {

  private static final String PATH = "/api/tasks/simpleTask/{taskId}";

  @Autowired
  private TaskService taskService;

  @GetMapping(path = PATH, produces = "application/json")
  public PayloadData loadData(@PathVariable("taskId") final String taskId) {
    log.info("Retrieving task payload for {}", taskId);
    return new PayloadData("Simple Task", "Hello world.");
  }

  @PostMapping(path = PATH, produces = "application/json", consumes = "application/json")
  public void submit(@PathVariable("taskId") final String taskId, final Map<String, Object> variables) {
    log.info("Submitting task {} with payload {}", taskId, variables);

    this.taskService.complete(taskId, variables);
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PayloadData {
    public String name;
    public String value;
  }
}
