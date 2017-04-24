package org.camunda.bpm.extension.cloud.broadcaster;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BroadcasterConfiguration {

  @Value("${camunda.bpm.cloud.amqp.queue}")
  private String queueName;

  @Value("${camunda.bpm.cloud.amqp.exchange}")
  private String exchangeName;

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
