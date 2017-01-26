package org.camunda.bpm.extension.cloud.event.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.event.service.client.CamundaNativeRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
public class ProcessResource {

  @Autowired
  private CamundaNativeRestClient camundaRestClient;

  @Autowired
  private DiscoveryClient discoveryClient;

  @RequestMapping(produces = "application/json", value = "/process-definition", method = RequestMethod.GET)
  public HttpEntity<Collection<ProcessDefinition>> getProcessDefinitions() {
    Collection<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>();
    getProcessEngines().forEach((ServiceInstance s) -> {
      camundaRestClient.getProcessDefinitions(s.getUri().toString()).forEach((ProcessDefinition processDefinition) -> {
        // processDefinition.setEngineUrl(s.getUri().toString());
        // hack needed, to prevent cross origin resource sharing problem
        processDefinition.setEngineUrl(String.format("http://localhost:%s/", s.getPort()));
        processDefinitions.add(processDefinition);
      });
    });
    return new HttpEntity<Collection<ProcessDefinition>>(processDefinitions);
  }

  private List<ServiceInstance> getProcessEngines() {
    List<String> services = discoveryClient.getServices();
    List<ServiceInstance> camundaEngines = new ArrayList<ServiceInstance>();
    for (String service : services) {
      ServiceInstance serviceInstance = discoveryClient.getInstances(service).get(0);
      if (serviceInstance.getMetadata().get("engine") != null
          && serviceInstance.getMetadata().get("engine").equals("camunda")) {
        camundaEngines.add(serviceInstance);
      }
    }
    return camundaEngines;
  }
}
