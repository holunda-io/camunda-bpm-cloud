package org.camunda.bpm.extension.cloud.broadcaster.listener;


import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.cloud.workload.service.task.command.TaskCommand;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractTaskListener<C extends TaskCommand> implements TaskListener {

  @Autowired
  protected FormService formService;

  @Autowired
  protected RabbitTemplate rabbit;

  @Value(value = "${spring.application.name}")
  protected String appName;

  @Value("${camunda.bpm.cloud.amqp.queue}")
  private String queueName;

  @Autowired
  public void register(final CamundaEventBus camundaEventBus) {
    camundaEventBus.register(this);
  }

  protected String getFormKey(DelegateTask delegateTask) {
    return formService.getTaskFormKey(
      delegateTask.getProcessDefinitionId(),
      delegateTask.getTaskDefinitionKey()
    );
  }

  protected abstract C createCommand(DelegateTask delegateTask);

  @Override
  public void notify(DelegateTask delegateTask) {
    final C command = createCommand(delegateTask);

    rabbit.convertAndSend(queueName, command);
  }
}
