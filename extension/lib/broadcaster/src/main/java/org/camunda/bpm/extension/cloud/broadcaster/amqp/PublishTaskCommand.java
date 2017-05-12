package org.camunda.bpm.extension.cloud.broadcaster.amqp;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.amqp.CamundaCloudAmqpConfigurationProperties;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class PublishTaskCommand implements Consumer<TaskCommand> {

  private final RabbitTemplate rabbitTemplate;

  private final String exchange;
  private final String routingKey;
  private final String name;

  public PublishTaskCommand(CamundaCloudAmqpConfigurationProperties properties, @Value("${spring.application.name}") String name, RabbitTemplate rabbitTemplate) {
    this.exchange = properties.getExchange();
    this.routingKey = properties.getQueue();

    this.name = name;

    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void accept(TaskCommand taskCommand) {
    rabbitTemplate.convertAndSend(exchange, routingKey, taskCommand);
    log.info("[{}] publish command on {}/{}: {}", name, exchange, routingKey, taskCommand);
  }
}
