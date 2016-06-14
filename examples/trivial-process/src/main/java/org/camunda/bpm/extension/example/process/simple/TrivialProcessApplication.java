package org.camunda.bpm.extension.example.process.simple;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.extension.cloud.broadcaster.BroadcasterConfiguration;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.camunda.bpm.extension.reactor.plugin.ReactorProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ProcessApplication
@EnableEurekaClient
@Import(BroadcasterConfiguration.class)
public class TrivialProcessApplication extends SpringBootProcessApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrivialProcessApplication.class, args);
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
}