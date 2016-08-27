package org.camunda.bpm.extension.cloud.event.service;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class EventService {

  @Autowired
  private EventCache eventCache;

  @Autowired
  private CamundaRestClient camundaRestClient;

  @Autowired
  private DiscoveryClient discoveryClient;

  public static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

  @RequestMapping(produces = "application/json", value = "/task", method = RequestMethod.GET)
  public HttpEntity<String> getTasks(){
    Collection<TaskEvent> events = eventCache.getEvents();
    return generateTaskCollectionJson(events);
  }

  private HttpEntity<String> generateTaskCollectionJson(Collection<TaskEvent> events){
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new HttpEntity<String>(new JSONArray(events) //
      .toString(), headers);
  }

  @RequestMapping(produces = "application/json", value = "/process-definition", method = RequestMethod.GET)
  public HttpEntity<String> getProcessDefinitions(){
    Collection<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>();
    getProcessEngines().forEach((ServiceInstance s) -> {
      camundaRestClient.getProcessDefinitions(s.getUri().toString()).forEach((ProcessDefinition processDefinition) -> {
        // processDefinition.setEngineUrl(s.getUri().toString());
        // hack needed, to prevent cross origin resource sharing problem
        processDefinition.setEngineUrl(String.format("http://localhost:%s/", s.getPort()));
        processDefinitions.add(processDefinition);
      });
    });
    return generateProcessDefinitionCollectionJson(processDefinitions);
  }

  private HttpEntity<String> generateProcessDefinitionCollectionJson(Collection<ProcessDefinition> processDefinitions){
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new HttpEntity<String>(new JSONArray(processDefinitions) //
      .toString(), headers);
  }

  private List<ServiceInstance> getProcessEngines(){
    List<String> services = discoveryClient.getServices();
    List<ServiceInstance> camundaEngines = new ArrayList<ServiceInstance>();
    for (String service : services) {
      ServiceInstance serviceInstance = discoveryClient.getInstances(service).get(0);
      if (serviceInstance.getMetadata().get("engine") != null && serviceInstance.getMetadata().get("engine").equals("camunda") ){
        camundaEngines.add(serviceInstance);
      }
    }
    return camundaEngines;
  }
}
