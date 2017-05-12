package org.camunda.bpm.extension.cloud.broadcaster;

import org.camunda.bpm.extension.cloud.amqp.CamundaCloudAmqpAutoConfiguration;
import org.camunda.bpm.extension.cloud.amqp.CamundaCloudAmqpConfigurationProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
public class BroadcasterConfiguration {


}
