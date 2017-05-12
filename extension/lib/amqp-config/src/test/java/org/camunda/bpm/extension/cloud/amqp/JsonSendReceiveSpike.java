package org.camunda.bpm.extension.cloud.amqp;


import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
  "camunda.bpm.cloud.amqp.queue=foo",
  "camunda.bpm.cloud.amqp.exchange=foo-exchange",
})
@SpringBootTest(classes = CamundaCloudAmqpAutoConfiguration.class)
@EnableRabbit
@Slf4j
public class JsonSendReceiveSpike {

  @Data
  @Builder
  public static class DummyCommand {

    private String name;

  }

  @Autowired
  protected RabbitTemplate rabbit;

  @Autowired
  private CamundaCloudAmqpConfigurationProperties properties;

  @Test
  public void name() throws Exception {
    log.info("send Hello World to {}", properties.getQueue());
    rabbit.convertAndSend(properties.getQueue(), DummyCommand.builder()
      .name("Kermit the Frog")
      .build());
  }
}
