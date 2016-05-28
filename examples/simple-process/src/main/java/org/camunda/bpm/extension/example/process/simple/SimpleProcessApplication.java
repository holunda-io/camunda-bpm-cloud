package org.camunda.bpm.extension.example.process.simple;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@ProcessApplication(name = "SimpleProcessApplication")
public class SimpleProcessApplication extends SpringBootProcessApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleProcessApplication.class, args);
  }

    @Bean
    public ResourceConfig jerseyConfig() {
      return new CamundaJerseyResourceConfig();
    }
}
