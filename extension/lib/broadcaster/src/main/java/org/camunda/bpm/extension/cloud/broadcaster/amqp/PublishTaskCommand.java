package org.camunda.bpm.extension.cloud.broadcaster.amqp;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.cloud.properties.CamundaCloudProperties;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class PublishTaskCommand implements Consumer<TaskCommand> {

  private final RabbitTemplate rabbitTemplate;

  private final String exchange;
  private final String routingKey;
  private final String name;

  public PublishTaskCommand(CamundaCloudProperties properties, RabbitTemplate rabbitTemplate) {
    this.exchange = properties.getAmqp().getExchange().getCommand();
    this.routingKey = properties.getAmqp().getQueue().getCommand();

    this.name = properties.getName();

    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void accept(TaskCommand taskCommand) {
    rabbitTemplate.convertAndSend(exchange, routingKey, taskCommand);
    log.info("[{}] publish command on {}/{}: {}", name, exchange, routingKey, taskCommand);
  }
}
