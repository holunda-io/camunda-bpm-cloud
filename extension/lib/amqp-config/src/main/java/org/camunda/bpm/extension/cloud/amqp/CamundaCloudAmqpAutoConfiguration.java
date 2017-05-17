package org.camunda.bpm.extension.cloud.amqp;

import org.camunda.bpm.cloud.properties.CamundaCloudProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableConfigurationProperties(CamundaCloudProperties.class)
public class CamundaCloudAmqpAutoConfiguration {

  @Autowired
  private CamundaCloudProperties properties;

  @Bean
  Queue queue() {
    return new Queue(properties.getAmqp().getQueue().getCommand(), true);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(properties.getAmqp().getExchange().getCommand());
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(properties.getAmqp().getQueue().getCommand());
  }

  @Bean
  Queue eventQueue() {
    return new Queue(properties.getAmqp().getQueue().getEvent(), true);
  }

  @Bean
  TopicExchange eventExchange() {
    return new TopicExchange(properties.getAmqp().getExchange().getEvent());
  }

  @Bean
  Binding eventBinding() {
    return BindingBuilder.bind(eventQueue())
      .to(eventExchange())
      .with(properties.getAmqp().getQueue().getEvent());
  }

  @Bean
  RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }


}
