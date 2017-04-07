package org.camunda.bpm.extension.cloud.workload.service.rest;

public class ProcessDefinition {

  private String key;
  private String name;
  private String engineUrl;

  public ProcessDefinition() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProcessDefinition(String key, String engineUrl, String name) {
    this.key = key;
    this.engineUrl = engineUrl;
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getEngineUrl() {
    return engineUrl;
  }

  public void setEngineUrl(String engineUrl) {
    this.engineUrl = engineUrl;
  }

  @Override

  public String toString() {
    return "ProcessDefinition{" +
      "key='" + key + '\'' +
      ", engineUrl='" + engineUrl + '\'' +
      '}';
  }
}
