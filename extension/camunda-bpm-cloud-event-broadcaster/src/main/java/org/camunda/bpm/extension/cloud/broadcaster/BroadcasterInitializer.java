package org.camunda.bpm.extension.cloud.broadcaster;

import javax.annotation.PostConstruct;

import org.camunda.bpm.extension.reactor.CamundaReactor;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.springframework.stereotype.Component;

@Component
public class BroadcasterInitializer {
  
  @PostConstruct
  public void initialize() {
    final CamundaEventBus eventBus = CamundaReactor.eventBus();
    eventBus.register(new TaskCreateListener());
    eventBus.register(new TaskCompleteListener());
    eventBus.register(new TaskDeleteListener());
  }
}
