package org.camunda.bpm.extension.cloud.workload.command.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
  Serializer serializer() {
    return new JacksonSerializer();
  }

  @Bean
  public SpringAMQPMessageSource myQueueMessageSource(final AMQPMessageConverter messageConverter) {
    return new SpringAMQPMessageSource(messageConverter) {

      @RabbitListener(queues = "${camunda.bpm.cloud.amqp.queue.command}")
      @Override
      public void onMessage(Message message, Channel channel) throws Exception {

        log.info("receiving command: {}#{}", message, channel);
        super.onMessage(message, channel);
      }
    };
  }
}
