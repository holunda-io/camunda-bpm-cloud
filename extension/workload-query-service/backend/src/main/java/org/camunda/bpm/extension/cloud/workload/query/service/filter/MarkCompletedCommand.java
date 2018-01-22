package org.camunda.bpm.extension.cloud.workload.query.service.filter;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.cloud.properties.CamundaCloudProperties;
import org.camunda.bpm.extension.cloud.workload.command.TaskCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class MarkCompletedCommand implements Consumer<TaskCommand> {

  private final RabbitTemplate rabbitTemplate;

  private final String exchange;
  private final String routingKey;
  private final String name;

  public MarkCompletedCommand(final CamundaCloudProperties properties, final RabbitTemplate rabbitTemplate) {
    this.exchange = properties.getAmqp().getExchange().getCommand();
    this.routingKey = properties.getAmqp().getQueue().getCommand();

    this.name = properties.getName();

    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void accept(final TaskCommand taskCommand) {
    this.rabbitTemplate.convertAndSend(this.exchange, this.routingKey, taskCommand);
    log.info("[{}] publish command on {}/{}: {}", this.name, this.exchange, this.routingKey, taskCommand);
  }
}
