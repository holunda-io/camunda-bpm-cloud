package org.camunda.bpm.extension.cloud.amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"camunda.bpm.cloud.amqp.queue=foo"})
@ContextConfiguration(classes = CamundaCloudAmqpConfigurationPropertiesTest.TestConfig.class)
public class CamundaCloudAmqpConfigurationPropertiesTest {

  @EnableConfigurationProperties(CamundaCloudAmqpConfigurationProperties.class)
  public static class TestConfig {

  }

  @Autowired
  private CamundaCloudAmqpConfigurationProperties properties;


  @Test
  public void queue_is_set() throws Exception {
    assertThat(properties.getQueue()).isEqualTo("foo");
  }
}
