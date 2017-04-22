package org.camunda.bpm.extension.configserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoadLocalPropertiesSpike.LoadLocalPropertiesApplication.class)
public class LoadLocalPropertiesSpike implements CommandLineRunner {


  @SpringBootApplication
  public static class LoadLocalPropertiesApplication {
    public static void main(String[] args) {
      SpringApplication.run(LoadLocalPropertiesApplication.class, args);
    }
  }

  private RestTemplate rest = new RestTemplate();




  @Test
  public void name() throws Exception {
    Map<String,String> config = rest.getForObject("http://localhost:8888/simpleprocess/default", Map.class);

    System.out.printf("-- " + config);
  }

  @Override
  public void run(String... args) throws Exception {

  }


  //  server.port: 0
//
//  spring:
//  application:
//  name: simpleprocess
//  cloud:
//  config:
//  uri: http://configserver:8888
//
//  eureka:
//  instance:
//  hostname: ${spring.application.name}
//  client:
//  registerWithEureka: true
//  fetchRegistry: true
//  serviceUrl:
//  defaultZone: http://discovery:8761/eureka/

}
