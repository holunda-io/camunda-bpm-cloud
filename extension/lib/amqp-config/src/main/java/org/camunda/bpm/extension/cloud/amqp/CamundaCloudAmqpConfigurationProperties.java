package org.camunda.bpm.extension.cloud.amqp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "camunda.bpm.cloud.amqp")
@Data
public class CamundaCloudAmqpConfigurationProperties {

  private String queue;
  private String exchange;

}
