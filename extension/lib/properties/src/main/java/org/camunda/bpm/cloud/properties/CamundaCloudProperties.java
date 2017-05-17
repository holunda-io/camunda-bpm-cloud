package org.camunda.bpm.cloud.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.swing.text.StyleConstants.Size;

@Data
@ConfigurationProperties(prefix = "camunda.bpm.cloud")
@Validated
public class CamundaCloudProperties implements Serializable {

  /**
   * Referenced in <pre>spring.factories</pre> to load properties automatically when on classpath.
   */
  @EnableConfigurationProperties(CamundaCloudProperties.class)
  public static class CamundaCloudPropertiesConfiguration {
  }

  @Data
  public static class AmqpProperty implements Serializable {

    @NotNull
    @Size(min=1)
    String prefix;

    @Valid
    QueueProperty queue = new QueueProperty();

    @Valid
    ExchangeProperty exchange = new ExchangeProperty();
  }

  @Data
  public static class QueueProperty implements Serializable {

    @NotNull
    @Size(min=1)
    String command;

    @NotNull
    @Size(min=1)
    String event;
  }

  @Data
  public static class ExchangeProperty implements Serializable {

    @NotNull
    @Size(min=1)
    String command;

    @NotNull
    @Size(min=1)
    String event;
  }

  @Data
  public static class HostnameProperty implements Serializable {

    @NotNull
    @Size(min=1)
    String mysql = "localhost";

    @NotNull
    @Size(min=1)
    String discovery = "localhost";

    @NotNull
    @Size(min=1)
    String configserver = "localhost";

    @NotNull
    @Size(min=1)
    String rabbitmq = "localhost";
  }

  @Valid
  HostnameProperty hostname = new HostnameProperty();

  @Valid
  AmqpProperty amqp = new AmqpProperty();

  @NotNull
  String name;

}
