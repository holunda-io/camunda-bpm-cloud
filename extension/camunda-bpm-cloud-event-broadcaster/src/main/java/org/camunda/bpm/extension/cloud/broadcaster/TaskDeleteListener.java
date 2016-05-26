package org.camunda.bpm.extension.cloud.broadcaster;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.reactor.bus.CamundaSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@CamundaSelector(type = "userTask", event = TaskListener.EVENTNAME_DELETE)
public class TaskDeleteListener implements TaskListener {

  private final static Logger LOGGER = LoggerFactory.getLogger(TaskDeleteListener.class);

  @Autowired
  private EventServiceClient client;

  @Override
  public void notify(DelegateTask delegateTask) {
    client.broadcastEvent(delegateTask);
    LOGGER.info("Task deleted: {}", delegateTask.getTaskDefinitionKey());
  }

}
