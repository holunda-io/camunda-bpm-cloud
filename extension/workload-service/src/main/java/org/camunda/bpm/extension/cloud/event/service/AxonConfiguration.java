package org.camunda.bpm.extension.cloud.event.service;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class AxonConfiguration {

  @Bean
  public EventStore eventStore(EventStorageEngine eventStorageEngine) {
    return new EmbeddedEventStore(eventStorageEngine);
  }

  @Bean
  public EventStorageEngine eventStorageEngine(){
    return new InMemoryEventStorageEngine();
  }

  @Bean
  public EventBus eventBus(EventStore eventStore){
    return eventStore;
  }
}
