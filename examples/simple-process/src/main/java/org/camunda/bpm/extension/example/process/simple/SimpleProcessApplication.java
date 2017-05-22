package org.camunda.bpm.extension.example.process.simple;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.extension.cloud.broadcaster.EnableCamundaTaskBroadcast;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableProcessApplication
@EnableEurekaClient
@EnableScheduling
@EnableCamundaTaskBroadcast
@Slf4j
public class SimpleProcessApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleProcessApplication.class, args);
  }

  @Autowired
  private RuntimeService runtimeService;

  @Scheduled(initialDelay = 20000L, fixedRate = 20000L)
  public void start() {
    log.info("Starting {}", runtimeService.startProcessInstanceByKey("SimpleProcess").getId());
  }

}
