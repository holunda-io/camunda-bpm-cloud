package org.camunda.bpm.extension.example.process.simple;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.extension.cloud.broadcaster.BroadcasterConfiguration;
import org.camunda.bpm.extension.example.process.template.AbstractExampleProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@ProcessApplication
@EnableEurekaClient
@Import(BroadcasterConfiguration.class)
@Slf4j
public class TrivialProcessApplication extends AbstractExampleProcessApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivialProcessApplication.class, args);
  }

  @Autowired
  private RuntimeService runtimeService;

  @Scheduled(initialDelay = 5000L, fixedRate = 20000L)
  public void start() {
    log.info("Starting {}", runtimeService.startProcessInstanceByKey("TrivialProcess").getId());
  }

}
