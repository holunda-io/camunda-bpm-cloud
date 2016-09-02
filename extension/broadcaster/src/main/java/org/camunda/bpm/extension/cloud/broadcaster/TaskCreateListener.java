package org.camunda.bpm.extension.cloud.broadcaster;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.cloud.broadcaster.EventServiceClient.EventType;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.camunda.bpm.extension.reactor.bus.CamundaSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@CamundaSelector(type = "userTask", event = TaskListener.EVENTNAME_CREATE)
@Slf4j
public class TaskCreateListener implements TaskListener {

  @Autowired
  private EventServiceClient client;

  @Autowired
  private FormService formService;

  @Autowired
  public void register(CamundaEventBus camundaEventBus) {
    camundaEventBus.register(this);
  }

  @Override
  public void notify(DelegateTask delegateTask) {
    final String formKey = formService.getTaskFormKey(delegateTask.getProcessDefinitionId(),
        delegateTask.getTaskDefinitionKey());
    client.broadcastEvent(delegateTask, EventType.CREATED, formKey);
    log.info("New task created: {}", delegateTask.getTaskDefinitionKey());
  }

}
