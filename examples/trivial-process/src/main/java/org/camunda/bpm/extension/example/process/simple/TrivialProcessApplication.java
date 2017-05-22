package org.camunda.bpm.extension.example.process.simple;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.camunda.bpm.extension.cloud.broadcaster.BroadcasterConfiguration;
import org.camunda.bpm.extension.cloud.broadcaster.EnableCamundaTaskBroadcast;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.camunda.bpm.extension.reactor.plugin.ReactorProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableProcessApplication
@EnableEurekaClient
@EnableCamundaTaskBroadcast
@Slf4j
public class TrivialProcessApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivialProcessApplication.class, args);
  }

  @Autowired
  private RuntimeService runtimeService;

  @Scheduled(initialDelay = 20000L, fixedRate = 20000L)
  public void start() {
    log.info("Starting {}", runtimeService.startProcessInstanceByKey("TrivialProcess").getId());
  }

}
