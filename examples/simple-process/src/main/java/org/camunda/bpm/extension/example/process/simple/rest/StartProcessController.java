package org.camunda.bpm.extension.example.process.simple.rest;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartProcessController {


  @Autowired
  private RuntimeService runtimeService;

  @GetMapping("/api/process/start")
  public String startProcess() {
    return runtimeService.startProcessInstanceByKey("SimpleProcess").getId();
  }
}
