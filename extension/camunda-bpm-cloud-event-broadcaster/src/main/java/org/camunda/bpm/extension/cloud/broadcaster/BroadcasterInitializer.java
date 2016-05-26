package org.camunda.bpm.extension.cloud.broadcaster;

import org.camunda.bpm.extension.reactor.CamundaReactor;
import org.camunda.bpm.extension.reactor.bus.CamundaEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BroadcasterInitializer {

  public static final Logger LOGGER = LoggerFactory.getLogger(BroadcasterInitializer.class);

  @Autowired
  private TaskCreateListener createListener;

  @Autowired
  private TaskDeleteListener deleteListener;

  @Autowired
  private TaskCompleteListener completeListener;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    final CamundaEventBus eventBus = CamundaReactor.eventBus();
    eventBus.register(createListener);
    eventBus.register(deleteListener);
    eventBus.register(completeListener);

    LOGGER.info("Reactor task listeners initialized.");

  }
}
