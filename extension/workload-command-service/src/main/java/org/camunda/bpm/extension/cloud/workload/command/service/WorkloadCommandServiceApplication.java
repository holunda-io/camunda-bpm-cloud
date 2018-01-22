package org.camunda.bpm.extension.cloud.workload.command.service;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.camunda.bpm.extension.cloud.workload.command.TaskCommand;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableRabbit
@Slf4j
public class WorkloadCommandServiceApplication {

  public static void main(final String... args) {
    SpringApplication.run(WorkloadCommandServiceApplication.class, args);
  }

  @Autowired
  private CommandGateway commandGateway;

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**");
      }
    };
  }

  @Bean
  Serializer serializer() {
    return new JacksonSerializer();
  }

  @RabbitListener(queues = "${camunda.bpm.cloud.amqp.queue.command}")
  public void receiveCommand(final TaskCommand command) {
    this.commandGateway.send(command);
    log.info("Received message from command queue: {} {}", command.getClass().getSimpleName(), command.toString());
  }

}
