package org.camunda.bpm.extension.cloud.event.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class CamundaRestClient {

  public static final Logger LOGGER = LoggerFactory.getLogger(CamundaRestClient.class);

  public List<ProcessDefinition> getProcessDefinitions(final String engineUrl){
    final String url = engineUrl + "/rest/engine/default/process-definition/";
    return new ProcessDefinitionsHytrixCommand(url).execute();
  }

}

class ProcessDefinitionsHytrixCommand extends HystrixCommand<List<ProcessDefinition>> {

  public static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionsHytrixCommand.class);

  private String url;

  private final RestTemplate rest = new RestTemplate();

  public ProcessDefinitionsHytrixCommand(final String url){
    super(HystrixCommandGroupKey.Factory.asKey("CamundaRestClient"));
    this.url = url;
  }

  @Override
  protected List<ProcessDefinition> run() throws Exception {
    ResponseEntity<List<ProcessDefinition>> processDefinitions =
      rest.exchange(url,
        HttpMethod.GET, null, new ParameterizedTypeReference<List<ProcessDefinition>>() {
        });
    return processDefinitions.getBody();
  }

  @Override
  protected List<ProcessDefinition> getFallback() {
    LOGGER.error("Retrieving process definitions failed.");
    return new ArrayList<ProcessDefinition>();
  }

}
