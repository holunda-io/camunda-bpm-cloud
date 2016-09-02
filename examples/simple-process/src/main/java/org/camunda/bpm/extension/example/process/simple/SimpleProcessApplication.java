package org.camunda.bpm.extension.example.process.simple;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.extension.cloud.broadcaster.BroadcasterConfiguration;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.camunda.bpm.extension.reactor.plugin.ReactorProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ProcessApplication
@EnableEurekaClient
@EnableScheduling
@Import(BroadcasterConfiguration.class)
@Slf4j
public class SimpleProcessApplication extends SpringBootProcessApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleProcessApplication.class, args);
  }

  @Bean
  public ResourceConfig jerseyConfig() {
    return new CamundaJerseyResourceConfig();
  }

  @Bean
  public static CamundaEventBus camundaEventBus() {
    return new CamundaEventBus();
  }

  @Bean
  public static StandaloneInMemProcessEngineConfiguration configuration(CamundaEventBus camundaEventBus) {
    return new StandaloneInMemProcessEngineConfiguration() {
      {
        this.getProcessEnginePlugins().add(new ReactorProcessEnginePlugin(camundaEventBus));
      }
    };
  }

  @Autowired
  private RuntimeService runtimeService;

  @Scheduled(initialDelay = 5000L, fixedRate = 20000L)
  public void start() {
    log.info("Starting {}", runtimeService.startProcessInstanceByKey("SimpleProcess").getId());
  }
}
