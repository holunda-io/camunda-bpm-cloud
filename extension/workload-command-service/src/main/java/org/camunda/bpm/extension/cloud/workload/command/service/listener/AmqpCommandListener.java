package org.camunda.bpm.extension.cloud.workload.command.service.listener;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.camunda.bpm.extension.cloud.workload.command.CreateTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.TaskCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Receives task commands from rabbit.
 */
@Component
@Slf4j
@Deprecated
public class AmqpCommandListener {

  private final CommandGateway commandGateway;

  public AmqpCommandListener(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  private void sendCommand(TaskCommand command) {
    log.info("Received: {}", command.toString());
    commandGateway.send(command);
  }

  @RabbitListener(queues = "${camunda.bpm.cloud.amqp.queue.command}")
  public void receiveCommand(final CreateTaskCommand command) {
    sendCommand(command);
  }

  // TODO: implement for all commands or come up with generic approach

}
