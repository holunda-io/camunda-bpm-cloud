package org.camunda.bpm.extension.example.process.simple.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SimpleTaskController {

  @GetMapping(path = "/api/tasks/simpleTask/{taskId}", produces = "application/json")
  public PayloadData loadData(@PathVariable("taskId") final String taskId) {
    log.info("Retrieving task payload for {}", taskId);
    return new PayloadData("Simple Task", "Hello world.");
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PayloadData {
    public String name;
    public String value;
  }
}
