package org.camunda.bpm.extension.cloud.broadcaster.listener;


import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.cloud.broadcaster.amqp.PublishTaskCommand;
import org.camunda.bpm.extension.cloud.workload.command.TaskCommand;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

public abstract class AbstractTaskListener<C extends TaskCommand> implements TaskListener {

  @Autowired
  protected PublishTaskCommand publishTaskCommand;

  @Autowired
  protected Optional<FormService> formService;

  @Value("${spring.application.name}")
  protected String appName;

  @Autowired
  public void register(final CamundaEventBus camundaEventBus) {
    camundaEventBus.register(this);
  }

  protected String getFormKey(DelegateTask delegateTask) {
    if (!formService.isPresent()) {
      return null;
    }

    return formService.get().getTaskFormKey(
      delegateTask.getProcessDefinitionId(),
      delegateTask.getTaskDefinitionKey()
    );
  }

  protected abstract C createCommand(DelegateTask delegateTask);

  @Override
  public void notify(DelegateTask delegateTask) {
    publishTaskCommand.accept(createCommand(delegateTask));
  }
}
