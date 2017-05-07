package org.camunda.bpm.extension.cloud.workload.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AxonConfiguration {

  @Bean
  public SpringAMQPMessageSource myQueueMessageSource(AMQPMessageConverter messageConverter) {
    return new SpringAMQPMessageSource(messageConverter) {

      @RabbitListener(queues = "${camunda.bpm.cloud.amqp.queue}")
      @Override
      public void onMessage(Message message, Channel channel) throws Exception {

        log.info("receiving: {}#{}", message, channel);
        super.onMessage(message, channel);
      }
    };
  }

}
