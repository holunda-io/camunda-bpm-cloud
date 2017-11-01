package org.camunda.bpm.extension.cloud.workload.query.service.fn;

import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ResolveTaskFormUrl.class, ExtractApplicationName.class})
@TestPropertySource(properties = {"camunda.bpm.cloud.taskUrlTemplate=http://%s.test.scope/%s.html?taskId=%s"})
public class ResolveTaskFormUrlTest {

  @Autowired
  private ResolveTaskFormUrl fn;

  @Test
  public void resolve_taskUrl() throws Exception {
    TaskQueryObject task = new TaskQueryObject();
    task.setTaskId("simple-process-558ccae1-bef3-11e7-b631-eeee0aff74c9");
    task.setFormKey("simpleTask");

    task = fn.apply(task);

    assertThat(task.getTaskFormUrl()).isNotNull();
    assertThat(task.getTaskFormUrl()).isEqualTo(new URL("http://simple-process.test.scope/simpleTask.html?taskId=simple-process-558ccae1-bef3-11e7-b631-eeee0aff74c9"));
  }
}
