package org.camunda.bpm.extension.cloud.workload.query.service.fn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ExtractApplicationNameTest {

  @Parameters(name = "{index} taskId={0} expected={1}")
  public static List<Object[]> data() {
    return Arrays.asList(new Object[][]{
      {"simple-process-02b46094-bef5-11e7-b631-eeee0aff74c9", "simple-process"},
      {"simpleprocess-02b46094-bef5-11e7-b631-eeee0aff74c9", "simpleprocess"}
    });
  }

  @Parameterized.Parameter(0)
  public String taskId;

  @Parameterized.Parameter(1)
  public String expectedApplicationName;

  private final ExtractApplicationName fn = new ExtractApplicationName();

  @Test
  public void evaluate() throws Exception {
    assertThat(fn.apply(taskId)).isEqualTo(expectedApplicationName);
  }
}
