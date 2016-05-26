package org.camunda.bpm.extension.example.process.simple;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@ProcessApplication(name = "SimpleProcessApplication")
public class SimpleProcessApplication extends SpringBootProcessApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleProcessApplication.class, args);
  }
}
