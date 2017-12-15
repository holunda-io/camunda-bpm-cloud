package org.camunda.bpm.extension.cloud.broadcaster.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.extension.cloud.workload.command.CreateTaskCommand;
import org.camunda.bpm.extension.reactor.bus.CamundaSelector;
import org.springframework.stereotype.Component;

@Component
@CamundaSelector(type = "userTask", event = TaskListener.EVENTNAME_CREATE)
@Slf4j
public class TaskCreateListener extends AbstractTaskListener<CreateTaskCommand> {


  @Override
  protected CreateTaskCommand createCommand(final DelegateTask task) {
    return CreateTaskCommand.builder()
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
      .engineId(this.appName)
      .eventType(task.getEventName())
      .tenantId(task.getTenantId())
      .build();
  }

  @Override
  public void notify(final DelegateTask delegateTask) {
    super.notify(delegateTask);
    log.info("New task created: {}", delegateTask.getTaskDefinitionKey());
  }

}
