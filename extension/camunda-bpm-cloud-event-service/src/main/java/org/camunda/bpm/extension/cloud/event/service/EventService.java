package org.camunda.bpm.extension.cloud.event.service;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class EventService {

  @Autowired
  private EventCache eventCache;

  @RequestMapping(produces = "application/json", value = "/eventService/task", method = RequestMethod.GET)
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

  @RequestMapping(produces = "application/json", value = "/eventService/process-definition", method = RequestMethod.GET)
  public HttpEntity<String> getProcessDefinitions(){
    Collection<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>() { // anonymous subclass
      { // engines/processDefinitions should be retrieved from service registry. For the time being, static content
        add(new ProcessDefinition("SimpleProcess", "http://localhost:8080/", "SimpleProcess"));
        add(new ProcessDefinition("TrivialProcess", "http://localhost:8082/", "TrivialProcess"));
      }
    };
    return generateProcessDefinitionCollectionJson(processDefinitions);
  }

  private HttpEntity<String> generateProcessDefinitionCollectionJson(Collection<ProcessDefinition> processDefinitions){
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new HttpEntity<String>(new JSONArray(processDefinitions) //
      .toString(), headers);
  }
}
