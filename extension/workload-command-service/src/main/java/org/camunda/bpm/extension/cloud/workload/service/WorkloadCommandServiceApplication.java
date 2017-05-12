package org.camunda.bpm.extension.cloud.workload.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.amqp.CamundaCloudAmqpConfigurationProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
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


  @Autowired
  private CamundaCloudAmqpConfigurationProperties properties;

  @Value("${spring.rabbitmq.host}")
  private String amqpHost;

  @Bean
  CommandLineRunner onStart() {
    return strings -> {
      log.error("connecting to amqp: {}", amqpHost);
    };
  }

}
