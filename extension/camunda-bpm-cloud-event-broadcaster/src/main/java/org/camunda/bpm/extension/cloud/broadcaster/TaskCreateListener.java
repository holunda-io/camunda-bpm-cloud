package org.camunda.bpm.extension.cloud.broadcaster;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.reactor.bus.CamundaSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CamundaSelector(type = "userTask", event = TaskListener.EVENTNAME_CREATE)
public class TaskCreateListener implements TaskListener {

  private final static Logger LOGGER = LoggerFactory.getLogger(TaskCreateListener.class);

  @Override
  public void notify(DelegateTask delegateTask) {
    LOGGER.info("New task created: {}", delegateTask.getTaskDefinitionKey());
  }

}
