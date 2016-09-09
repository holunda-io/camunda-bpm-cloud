package org.camunda.bpm.extension.cloud.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class TasklistApplication {

  public static void main(String... args) {
    SpringApplication.run(TasklistApplication.class, args);
  }
}
