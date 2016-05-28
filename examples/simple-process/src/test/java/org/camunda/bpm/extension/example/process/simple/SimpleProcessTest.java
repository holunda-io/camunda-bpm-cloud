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

public class SimpleProcessTest {

  @Rule
  public ProcessEngineNeedleRule rule = new ProcessEngineNeedleRuleBuilder(this).build();

  @Inject
  RuntimeService runtimeService;

  @Inject
  TaskService taskService;

  @Test
  @Deployment(resources = "simple-process.bpmn")
  public void testDeploy() {
    // nothing to do.
  }

  @Test
  @Deployment(resources = "simple-process.bpmn")
  public void testStart() {
    Task task = startInstanceAndGetTask();

    assertNotNull(task);
    assertEquals("user_simpleTask", task.getTaskDefinitionKey());
  }

  @Test
  @Deployment(resources = "simple-process.bpmn")
  public void testCompleteTask() {
    Task task = startInstanceAndGetTask();

    taskService.complete(task.getId());

    assertNull(runtimeService.createProcessInstanceQuery().active().singleResult());
  }

  private Task startInstanceAndGetTask() {
    ProcessInstance instance = runtimeService.startProcessInstanceByKey("SimpleProcess");
    assertNotNull(instance);

    return taskService.createTaskQuery().active().singleResult();
  }
}
