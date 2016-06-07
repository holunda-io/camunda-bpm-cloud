package org.camunda.bpm.extension.example.process.simple;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.needle.ProcessEngineNeedleRule;
import org.camunda.bpm.extension.needle.ProcessEngineNeedleRuleBuilder;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Deployment(resources = "trivial-process.bpmn")
public class TrivialProcessTest {

  @Rule
  public ProcessEngineNeedleRule rule = new ProcessEngineNeedleRuleBuilder(this).build();

  @Inject
  RuntimeService runtimeService;

  @Inject
  TaskService taskService;

  @Test
  public void testDeploy() {
    // nothing to do.
  }

  @Test
  public void testStart() {
    Task task = startInstanceAndGetTask();

    assertNotNull(task);
    assertEquals("user_trivialTask", task.getTaskDefinitionKey());
  }

  @Test
  public void testCompleteTask() {
    Task task = startInstanceAndGetTask();

    taskService.complete(task.getId());

    assertNull(runtimeService.createProcessInstanceQuery().active().singleResult());
  }

  private Task startInstanceAndGetTask() {
    ProcessInstance instance = runtimeService.startProcessInstanceByKey("TrivialProcess");
    assertNotNull(instance);

    return taskService.createTaskQuery().active().singleResult();
  }
}
