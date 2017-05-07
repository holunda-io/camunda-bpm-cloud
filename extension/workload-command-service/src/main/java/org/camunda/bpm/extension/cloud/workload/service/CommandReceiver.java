package org.camunda.bpm.extension.cloud.workload.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.workload.service.task.command.CreateTaskCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommandReceiver {

  @RabbitListener(queues = "${camunda.bpm.cloud.amqp.queue}")
  public void handleCreateTaskCommand(CreateTaskCommand command) {
    log.info("received: {}", command);
  }

}
