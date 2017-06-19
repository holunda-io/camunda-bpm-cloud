package org.camunda.bpm.cloud.spike.amqp;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.broadcaster.EnableCamundaTaskBroadcast;
import org.camunda.bpm.extension.cloud.broadcaster.amqp.PublishTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.CreateTaskCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

@SpringBootApplication
@EnableScheduling
@EnableCamundaTaskBroadcast
@Slf4j
public class SendToRabbitApplication {
  public static void main(String[] args) {
    SpringApplication.run(SendToRabbitApplication.class, args);
  }

  @Autowired
  private PublishTaskCommand publishTaskCommand;

  @Scheduled(fixedDelay = 2000L)
  public void send() throws Exception {
    CreateTaskCommand command = CreateTaskCommand.builder()
      .taskId(UUID.randomUUID().toString())
      .build();

    publishTaskCommand.accept(command);

  }
}
