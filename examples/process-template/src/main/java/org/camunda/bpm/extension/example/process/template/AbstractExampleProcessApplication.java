package org.camunda.bpm.extension.example.process.template;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.camunda.bpm.extension.reactor.plugin.ReactorProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.springframework.context.annotation.Bean;


public abstract class AbstractExampleProcessApplication extends SpringBootProcessApplication {

  @Bean
  public static CamundaEventBus camundaEventBus() {
    return new CamundaEventBus();
  }

  @Bean
  public static ReactorProcessEnginePlugin reactorProcessEnginePlugin(CamundaEventBus camundaEventBus) {
    return new ReactorProcessEnginePlugin(camundaEventBus);
  }

  @Bean
  public static ProcessEnginePlugin uuidGenerator() {
    return new AbstractProcessEnginePlugin(){
      @Override
      public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setIdGenerator(new StrongUuidGenerator());
      }
    };
  }
}
