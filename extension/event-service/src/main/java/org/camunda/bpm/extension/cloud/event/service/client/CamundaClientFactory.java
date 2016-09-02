package org.camunda.bpm.extension.cloud.event.service.client;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import feign.Feign;
import feign.codec.Encoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CamundaClientFactory {

  @Autowired
  private DiscoveryClient discovery;

  /**
   * Retrieves a feign client for the engine.
   * 
   * @param engineId
   *          engine id as registered in the discovery.
   * @return service.
   */
  public Optional<CamundaRestClient> getClientForEngine(final String engineId) {

    final List<ServiceInstance> serviceInstances = discovery.getInstances(engineId);
    if (!serviceInstances.isEmpty()) {

      for (ServiceInstance serviceInstance : serviceInstances) {
        // TODO LB!
        final String engineMetadata = serviceInstance.getMetadata().get("engine");
        if (engineMetadata != null && engineMetadata.equals("camunda")) {
          log.info("Found engine id:{} uri:{} meta:{}", serviceInstance.getServiceId(), serviceInstance.getUri(),
              serviceInstance.getMetadata());
          return Optional
              .of(Feign.builder()
                  .logger(new Slf4jLogger())
                  .target(CamundaRestClient.class, serviceInstance.getUri().toString()));
        }
      }
    }
    return Optional.empty();

  }

  /**
   * Retrieves all Camunda BPM Engines registered in the discovery service.
   * 
   * @return Map with serviceId as key and engine instance as value.
   */
  public Map<String, ServiceInstance> getProcessEngines() {
    final Map<String, ServiceInstance> camundaEngines = Maps.newConcurrentMap();

    // discovery.getServices().stream().map(service ->
    // discovery.getInstances(service).get(0)).filter(serviceInstance ->
    // serviceInstance.getMetadata().get("engine"));

    // TODO functional!
    for (String service : discovery.getServices()) {
      // TODO LB!
      final ServiceInstance serviceInstance = discovery.getInstances(service).get(0);
      final String engineMetadata = serviceInstance.getMetadata().get("engine");
      if (engineMetadata != null && engineMetadata.equals("camunda")) {
        camundaEngines.put(serviceInstance.getServiceId(), serviceInstance);
        log.info("Found engine id:{} uri:{} meta:{}", serviceInstance.getServiceId(), serviceInstance.getUri(),
            serviceInstance.getMetadata());
      }
    }
    return camundaEngines;
  }

}
