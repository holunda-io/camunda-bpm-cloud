package org.camunda.bpm.extension.cloud.workload.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
@EnableRabbit
@Slf4j
public class WorkloadCommandServiceApplication {

  public static void main(String... args) {
    SpringApplication.run(WorkloadCommandServiceApplication.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**");
      }
    };
  }

  @Value("${camunda.bpm.cloud.amqp.queue}")
  private String queueName;

  @Value("${camunda.bpm.cloud.amqp.exchange}")
  private String exchangeName;

  @Value("${spring.rabbitmq.host}")
  private String amqpHost;

  @Bean
  CommandLineRunner onStart() {
    return strings -> {
      log.error("connecting to amqp: {}", amqpHost);
    };
  }

  @Bean
  Queue queue() {
    return new Queue(queueName, true);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(exchangeName);
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(queueName);
  }

}
