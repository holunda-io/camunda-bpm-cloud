package org.camunda.bpm.extension.cloud.event.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventServiceApplication {

  public static void main(String... args) {
    SpringApplication.run(EventServiceApplication.class, args);
  }

  @Bean
  public EventCache eventCache() {
    return new EventCache();
  }
}