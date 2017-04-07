package org.camunda.bpm.extension.cloud.workload.service.task.query.repository;

import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskCreatedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskMarkedForCompletionEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.TaskSentToBeCompletedEvent;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskCompletedEventFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskCreatedEventFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskMarkedForCompletionFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.common.fixture.TaskSentToBeCompletedEventFixture;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.service.task.query.TaskQueryObjectStateEnum;
import org.camunda.bpm.extension.cloud.workload.service.task.query.fixture.TaskQueryObjectFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskQueryObjectUpdaterTest {

  @Mock
  TaskQueryObjectRepository taskQueryObjectRepository;

  @InjectMocks
  TaskQueryObjectUpdater taskQueryObjectUpdater;

  @Test
  public void onTaskCreatedEvent_isSaved() throws Exception {
    TaskCreatedEvent taskCreatedEvent = TaskCreatedEventFixture.fooTaskCreatedEvent();

    taskQueryObjectUpdater.on(taskCreatedEvent);

    verify(taskQueryObjectRepository).save(any(TaskQueryObject.class));
  }

  @Test
  public void onTaskMarkedForCompletionEvent_stateIsSetToPendingToCompleteAndPersisted() throws Exception {
    TaskMarkedForCompletionEvent taskMarkedForCompletionEvent = TaskMarkedForCompletionFixture.fooTaskMarkedForCompletion();
    TaskQueryObject task = TaskQueryObjectFixture.fooTaskQueryObjectCreated();
    when(taskQueryObjectRepository.findOne(taskMarkedForCompletionEvent.getTaskId())).thenReturn(task);

    taskQueryObjectUpdater.on(taskMarkedForCompletionEvent);

    assertThat(task.getEventType()).isEqualTo(TaskQueryObjectStateEnum.PENDING_TO_COMPLETE);
    verify(taskQueryObjectRepository).save(task);
  }

  @Test
  public void onTaskSentToBeCompletedEvent_stateIsSetToSentToComplete() throws Exception {
    TaskSentToBeCompletedEvent taskSentToBeCompletedEvent = TaskSentToBeCompletedEventFixture.fooTaskSentToBeCompletedEvent();
    TaskQueryObject task = TaskQueryObjectFixture.fooTaskQueryObjectPendingToComplete();
    when(taskQueryObjectRepository.findOne(taskSentToBeCompletedEvent.getTaskId())).thenReturn(task);

    taskQueryObjectUpdater.on(taskSentToBeCompletedEvent);

    assertThat(task.getEventType()).isEqualTo(TaskQueryObjectStateEnum.COMPLETED_SENT);
    verify(taskQueryObjectRepository).save(task);
  }

  @Test
  public void onTaskCompletedEvent_stateIsSetToComplete() throws Exception {
    TaskCompletedEvent taskCompletedEvent = TaskCompletedEventFixture.fooTaskCompletedEvent();

    taskQueryObjectUpdater.on(taskCompletedEvent);

    verify(taskQueryObjectRepository).delete(taskCompletedEvent.getTaskId());
  }

}
