package org.camunda.bpm.extension.cloud.workload.service.task.command.executor;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.camunda.bpm.extension.cloud.workload.service.camunda.client.CamundaClientFactory;
import org.camunda.bpm.extension.cloud.workload.service.camunda.client.CamundaRestClient;
import org.camunda.bpm.extension.cloud.workload.service.task.command.command.SendTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObjectStateEnum;
import org.camunda.bpm.extension.cloud.workload.service.task.query.repository.TaskQueryObjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by pschalk on 09.09.16.
 */
@Slf4j
@Component
public class TaskExecutor {

  @Autowired
  private TaskQueryObjectRepository taskQueryObjectRepository;

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private CamundaClientFactory camundaClientBuilder;

  @Scheduled(initialDelay = 10000L, fixedRate = 5000L)
  public void work()
  {
    log.info("Trying to get tasks from queue");
    final Collection<TaskQueryObject> tasksPendingToComplete = taskQueryObjectRepository.findByEventType(TaskQueryObjectStateEnum.PENDING_TO_COMPLETE);
    log.info("Found {} pending tasks", tasksPendingToComplete.size());
    tasksPendingToComplete.stream().forEach(this::completeTask);
  }

  private void completeTask(final TaskQueryObject taskQueryObject) {
    final Optional<CamundaRestClient> clientForEngine = camundaClientBuilder.getClientForEngine(taskQueryObject.getEngineId());
    if (clientForEngine.isPresent()) {

      try {
        log.info("Trying to complete task {} on the engine {}", taskQueryObject.getTaskId(), taskQueryObject.getEngineId());
        clientForEngine.get().completeTask(taskQueryObject.getTaskId(), "{}");
      } catch (Exception e) {
        if (e.getCause() instanceof FeignException && ((FeignException) e.getCause()).status() == 500) {
          log.info("Status 500 ... TaskQueryObject not found ... Removing it from Queue");
          taskQueryObjectRepository.delete(taskQueryObject);
        }
        else
          log.error("Unexpected error ...", e);
      }
    } else {
      log.warn("No Engine found for id {}", taskQueryObject.getEngineId());
    }
    sendSendTaskForCompletionCommand(taskQueryObject);
  }

  private void sendSendTaskForCompletionCommand(TaskQueryObject taskQueryObject) {
    SendTaskForCompletionCommand sendTaskForCompletionCommand = new SendTaskForCompletionCommand();
    BeanUtils.copyProperties(taskQueryObject, sendTaskForCompletionCommand);
    commandGateway.send(sendTaskForCompletionCommand);
  }
}
