package org.camunda.bpm.extension.cloud.workload.service;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WorkloadQueryServiceApplication {

  public static void main(String... args) {
    SpringApplication.run(WorkloadQueryServiceApplication.class, args);
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
  public org.springframework.amqp.core.Exchange exchange(){
    return ExchangeBuilder.fanoutExchange("TaskEvents").build();
  }

  @Bean
  public Queue queue(){
    return QueueBuilder.durable("TaskEvents").build();
  }

  @Bean
  public Binding binding() {
    return BindingBuilder.bind(queue()).to(exchange()).with("*").noargs();
  }

  @Autowired
  private void configure(AmqpAdmin admin) {
    admin.declareExchange(exchange());
    admin.declareQueue(queue());
    admin.declareBinding(binding());
  }
}
