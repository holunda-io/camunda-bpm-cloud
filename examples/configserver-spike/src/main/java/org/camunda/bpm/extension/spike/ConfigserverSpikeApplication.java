package org.camunda.bpm.extension.spike;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

@SpringBootApplication
public class ConfigserverSpikeApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(ConfigserverSpikeApplication.class, args);
  }

  @Autowired
  private Environment environment;

  @Value("${spring.datasource.url}")
  private String dbUrl;

  private Map<String, Object> properties() {
    Map<String, Object> map = new HashMap();

    if (environment instanceof ConfigurableEnvironment) {
      for (PropertySource<?> propertySource : ((ConfigurableEnvironment) environment).getPropertySources()) {
        if (propertySource instanceof EnumerablePropertySource) {
          for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
            if (StringUtils.startsWithAny(key, "spring", "eureka", "camunda.bpm")) {
              map.put(key, propertySource.getProperty(key));
            }
          }
        }
      }
    }

    return map;
  }

  @Override
  public void run(String... args) throws Exception {
    properties().entrySet().forEach(System.out::println);

    System.out.println("dbUrl=" + dbUrl);
  }
}
