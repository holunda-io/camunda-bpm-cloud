package org.camunda.bpm.extension.example.process.simple;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.extension.cloud.broadcaster.BroadcasterConfiguration;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.camunda.bpm.extension.reactor.plugin.ReactorProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
@ProcessApplication
@EnableEurekaClient
@EnableScheduling
@Import(BroadcasterConfiguration.class)
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

  private final Logger logger = getLogger(this.getClass());

  @Autowired
  private RuntimeService runtimeService;

  @Scheduled(initialDelay = 5000L, fixedRate = 2000L)
  public void start() {
    logger.info("starting {}",
    runtimeService.startProcessInstanceByKey("SimpleProcess").getId());
  }
}
