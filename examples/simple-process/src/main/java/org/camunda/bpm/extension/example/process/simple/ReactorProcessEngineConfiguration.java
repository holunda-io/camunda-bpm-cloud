package org.camunda.bpm.extension.example.process.simple;

import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.extension.cloud.broadcaster.BroadcasterConfiguration;
import org.camunda.bpm.extension.reactor.CamundaReactor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BroadcasterConfiguration.class)
public class ReactorProcessEngineConfiguration {

  @Bean
  public StandaloneInMemProcessEngineConfiguration configuration() {
    return new StandaloneInMemProcessEngineConfiguration() {
      {
        this.getProcessEnginePlugins().add(CamundaReactor.plugin());
      }
    };
  }
}
