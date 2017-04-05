package org.camunda.bpm.extension.cloud.workload.service.executor;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.workload.service.task.query.repository.TaskQueryObjectCache;
import org.camunda.bpm.extension.cloud.workload.service.camunda.client.CamundaClientFactory;
import org.camunda.bpm.extension.cloud.workload.service.camunda.client.CamundaRestClient;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObjectStateEnum;
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
  private TaskQueryObjectCache taskQueryObjectCache;

  @Autowired
  private CamundaClientFactory camundaClientBuilder;

  @Scheduled(initialDelay = 10000L, fixedRate = 5000L)
  public void work()
  {
    log.info("Trying to get tasks from queue");
    final Collection<TaskQueryObject> events = taskQueryObjectCache.getEvents(TaskQueryObjectStateEnum.PENDING_TO_COMPLETE);
    log.info("Found {} pending tasks", events.size());
    events.stream().forEach(this::completeTask);
  }

  private void completeTask(final TaskQueryObject taskQueryObject) {
    final Optional<CamundaRestClient> clientForEngine = camundaClientBuilder.getClientForEngine(taskQueryObject.getEngineId());
    if (clientForEngine.isPresent()) {

      try {
        log.info("Completing taskQueryObject {} on the engine", taskQueryObject.getTaskId(), taskQueryObject.getEngineId());
        clientForEngine.get().completeTask(taskQueryObject.getTaskId(), "{}");
        taskQueryObject.setEventType(taskQueryObject.getEventType().next());
      } catch (Exception e) {
        if (e.getCause() instanceof FeignException && ((FeignException) e.getCause()).status() == 500) {
          log.info("Status 500 ... TaskQueryObject not found ... Removing it from Queue");
          taskQueryObjectCache.removeEvent(taskQueryObject);
        }
        else
          log.error("Unexpected error ...", e);
      }
    } else {
      log.warn("No Engine found for id {}", taskQueryObject.getEngineId());
    }
  }
}
