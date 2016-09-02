package org.camunda.bpm.extension.example.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class DiscoveryConfigClientApplication {

  public static void main(final String... args) {
    SpringApplication.run(DiscoveryConfigClientApplication.class, args);
  }

  @Component
  public static class Config {

    @Value("${foo.name}")
    private String name;

    public String getName() {
      return name;
    }
  }

  @Autowired
  private Config config;

  @RequestMapping("/name")
  public String getName() {
    return String.format("read from config: %s", config.getName());
  }
}
