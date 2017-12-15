package org.camunda.bpm.extension.cloud.workload.query.service;

import com.netflix.zuul.ZuulFilter;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.camunda.bpm.extension.cloud.workload.query.service.filter.MarkCompletedCommand;
import org.camunda.bpm.extension.cloud.workload.query.service.filter.MarkCompletedZuulFilter;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObjectRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableRabbit
@EnableZuulProxy
@Slf4j
public class WorkloadQueryServiceApplication {

  public static void main(final String... args) {
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
  Serializer serializer() {
    return new JacksonSerializer();
  }

  @Autowired
  public void configure(final EventHandlingConfiguration ehConfig, final SpringAMQPMessageSource myMessageSource) {
    ehConfig.registerSubscribingEventProcessor("org.camunda.bpm.extension.cloud.workload.query.service.handler", c -> myMessageSource);
  }

  @Bean
  public SpringAMQPMessageSource myMessageSource(final Serializer serializer, final EventHandlingConfiguration ehConfig) {
    return new SpringAMQPMessageSource(serializer) {
      @RabbitListener(queues = "${camunda.bpm.cloud.amqp.queue.event}")
      @Override
      public void onMessage(final Message message, final Channel channel) throws Exception {
        log.info("receiving event: {}#{}", message, channel);
        super.onMessage(message, channel);
      }
    };
  }

  @Bean
  public ZuulFilter markCompletedFilter(final TaskQueryObjectRepository repository, final MarkCompletedCommand command) {
    return new MarkCompletedZuulFilter(repository, command);
  }

  @Bean
  public PatternServiceRouteMapper serviceRouteMapper() {
    return new PatternServiceRouteMapper(
      "(?<name>^.+)-(?<version>v.+$)",
      "${version}/${name}");
  }

}
