package org.camunda.bpm.extension.cloud.event.service.executor;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.event.service.EventCache;
import org.camunda.bpm.extension.cloud.event.service.client.CamundaClientFactory;
import org.camunda.bpm.extension.cloud.event.service.client.CamundaRestClient;
import org.camunda.bpm.extension.cloud.event.service.rest.Task;
import org.camunda.bpm.extension.cloud.event.service.rest.TaskStateEnum;
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
  private EventCache eventCache;

  @Autowired
  private CamundaClientFactory camundaClientBuilder;

  @Scheduled(initialDelay = 10000L, fixedRate = 5000L)
  public void work()
  {
    log.info("Trying to get tasks from queue");
    final Collection<Task> events = eventCache.getEvents(TaskStateEnum.PENDING_TO_COMPLETE);
    log.info("Found {} pending tasks", events.size());
    events.stream().forEach(e -> completeTask(e));
  }

  private void completeTask(final Task task) {
    final Optional<CamundaRestClient> clientForEngine = camundaClientBuilder.getClientForEngine(task.getEngineId());
    if (clientForEngine.isPresent()) {

      try {
        log.info("Completing task {} on the engine", task.getTaskId(), task.getEngineId());
        clientForEngine.get().completeTask(task.getTaskId(), "{}");
        task.setEventType(task.getEventType().next());
      } catch (Exception e) {
        if (e.getCause() instanceof FeignException && ((FeignException) e.getCause()).status() == 500) {
          log.info("Status 500 ... Task not found ... Removing it from Queue");
          eventCache.removeEvent(task);
        }
        else
          log.error("Unexpected error ...", e);
      }
    } else {
      log.warn("No Engine found for id {}", task.getEngineId());
    }
  }
}
