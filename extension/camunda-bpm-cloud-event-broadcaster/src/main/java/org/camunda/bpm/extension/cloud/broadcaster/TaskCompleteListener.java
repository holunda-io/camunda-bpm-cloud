package org.camunda.bpm.extension.cloud.broadcaster;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.cloud.broadcaster.EventServiceClient.EventType;
import org.camunda.bpm.extension.reactor.bus.CamundaSelector;
import org.camunda.bpm.extension.reactor.spring.listener.ReactorTaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@CamundaSelector(type = "userTask", event = TaskListener.EVENTNAME_COMPLETE)
public class TaskCompleteListener extends ReactorTaskListener {

  private final static Logger LOGGER = LoggerFactory.getLogger(TaskCompleteListener.class);

  @Autowired
  private EventServiceClient client;

  @Autowired
  private FormService formService;

  @Override
  public void notify(DelegateTask delegateTask) {
    final String formKey = formService.getTaskFormKey(delegateTask.getProcessDefinitionId(), delegateTask.getTaskDefinitionKey());
    client.broadcastEvent(delegateTask, EventType.COMPLETED, formKey);
    LOGGER.info("Task completed: {}", delegateTask.getTaskDefinitionKey());
  }

}
