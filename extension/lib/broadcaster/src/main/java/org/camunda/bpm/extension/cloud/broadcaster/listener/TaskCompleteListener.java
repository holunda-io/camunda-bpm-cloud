package org.camunda.bpm.extension.cloud.broadcaster.listener;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.cloud.broadcaster.EventServiceClient;
import org.camunda.bpm.extension.cloud.broadcaster.EventServiceClient.EventType;
import org.camunda.bpm.extension.cloud.workload.service.task.command.CompleteTaskCommand;
import org.camunda.bpm.extension.cloud.workload.service.task.command.DeleteTaskCommand;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.camunda.bpm.extension.reactor.bus.CamundaSelector;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@CamundaSelector(type = "userTask", event = TaskListener.EVENTNAME_COMPLETE)
@Slf4j
public class TaskCompleteListener extends AbstractTaskListener<CompleteTaskCommand> {

  @Override
  protected CompleteTaskCommand createCommand(DelegateTask task) {
    return CompleteTaskCommand.builder()
      .taskId(task.getId())
      .taskDefinitionKey(task.getTaskDefinitionKey())
      .name(task.getName())
      .description(task.getDescription())
      .priority(task.getPriority())
      .assignee(task.getAssignee())
      .owner(task.getOwner())
      .processInstanceId(task.getProcessInstanceId())
      .caseDefinitionId(task.getCaseDefinitionId())
      .caseExecutionId(task.getCaseExecutionId())
      .createTime(task.getCreateTime())
      .dueDate(task.getDueDate())
      .formKey(getFormKey(task))
      .engineId(appName)
      .eventType(TaskListener.EVENTNAME_CREATE)
      .tenantId(task.getTenantId())
      .build();
  }

  @Override
  public void notify(DelegateTask delegateTask) {
    super.notify(delegateTask);
    log.info("Task completed: {}", delegateTask.getId());
  }

}
