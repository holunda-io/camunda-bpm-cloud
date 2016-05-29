package org.camunda.bpm.extension.cloud.broadcaster;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@ConfigurationProperties(prefix="server")
public class BroadcasterConfiguration {

  private String port;

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getEngineUrl(){
    return getPort() != null ? String.format("http://localhost:%s/",getPort()) : "http://localhost:8080/";
  }

}
