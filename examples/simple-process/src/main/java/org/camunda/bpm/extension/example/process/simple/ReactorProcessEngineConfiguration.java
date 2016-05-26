package org.camunda.bpm.extension.example.process.simple;

import javax.inject.Inject;

import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.extension.cloud.broadcaster.BroadcasterConfiguration;
import org.camunda.bpm.extension.cloud.broadcaster.BroadcasterInitializer;
import org.camunda.bpm.extension.reactor.CamundaReactor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BroadcasterConfiguration.class)
public class ReactorProcessEngineConfiguration {

//  @Inject
//  BroadcasterInitializer initializer;

  @Bean
  public StandaloneInMemProcessEngineConfiguration configuration() {
//    initializer.initialize();
    return new StandaloneInMemProcessEngineConfiguration() {
      {
        this.getProcessEnginePlugins().add(CamundaReactor.plugin());
      }
    };
  }
}
