package org.camunda.bpm.extension.cloud.workload.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.camunda.bpm.cloud.properties.CamundaCloudProperties;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

  @Bean
  public SpringAMQPMessageSource myQueueMessageSource(AMQPMessageConverter messageConverter) {
    return new SpringAMQPMessageSource(messageConverter) {

      @RabbitListener(queues = "${camunda.bpm.cloud.amqp.queue.command}")
      @Override
      public void onMessage(Message message, Channel channel) throws Exception {

        log.info("receiving: {}#{}", message, channel);
        super.onMessage(message, channel);
      }
    };
  }

  @Autowired
  private CamundaCloudProperties properties;

  @Value("${spring.rabbitmq.host}")
  private String amqpHost;

  @Bean
  CommandLineRunner onStart() {
    return strings -> {
      log.error("connecting to amqp: {}", amqpHost);
    };
  }

}
