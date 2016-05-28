package org.camunda.bpm.extension.cloud.event.service;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class EventService {

  @Autowired
  private EventCache eventCache;

  @RequestMapping(produces = "application/json", value = "/eventService/tasks", method = RequestMethod.GET)
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
}
