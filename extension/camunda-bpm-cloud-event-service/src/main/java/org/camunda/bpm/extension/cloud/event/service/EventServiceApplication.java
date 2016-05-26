package org.camunda.bpm.extension.cloud.event.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventServiceApplication {
  
  public static void main(String... args) {
    SpringApplication.run(EventServiceApplication.class, args);
  }
}
