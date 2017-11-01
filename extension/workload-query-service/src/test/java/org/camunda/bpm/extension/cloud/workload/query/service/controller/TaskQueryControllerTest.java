package org.camunda.bpm.extension.cloud.workload.query.service.controller;

import org.camunda.bpm.extension.cloud.workload.query.service.fn.ResolveTaskFormUrl;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObjectRepository;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TaskQueryControllerTest {

  private static final String APP_NAME = "simple-process";
  private static final String TASK_ID = APP_NAME + "-1-2-3-4-5";
  private static final String FORM_KEY = "simpleTask";
  private static final String TASK_URL = "http://" + APP_NAME + ".test/" + FORM_KEY + "?taskId=" + TASK_ID;

  @Rule
  public final MockitoRule mockito = MockitoJUnit.rule();

  @InjectMocks
  private TaskQueryController taskQueryController;

  @Mock
  private TaskQueryObjectRepository repository;

  @Mock
  private ResolveTaskFormUrl resolveTaskFormUrl;

  @Test
  public void getTask_withe_url() throws Exception {
    TaskQueryObject task = new TaskQueryObject();
    task.setTaskId(TASK_ID);
    task.setFormKey(FORM_KEY);

    when(repository.findAll()).thenReturn(Arrays.asList(task));
    when(resolveTaskFormUrl.apply(any())).then((Answer) invocationOnMock -> {
      TaskQueryObject arg = invocationOnMock.getArgumentAt(0, TaskQueryObject.class);

      arg.setTaskFormUrl(new URL(TASK_URL));

      return arg;
    });

    List<TaskQueryObject> tasks = taskQueryController.getAllTasks();

    assertThat(tasks).hasSize(1);

    assertThat(tasks.get(0).getTaskFormUrl()).isEqualTo(new URL(TASK_URL));
  }
}
