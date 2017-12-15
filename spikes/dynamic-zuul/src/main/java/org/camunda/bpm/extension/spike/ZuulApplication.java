package org.camunda.bpm.extension.spike;

import org.camunda.bpm.extension.spike.filter.BarFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {

  public static void main(final String[] args) {
    SpringApplication.run(ZuulApplication.class, args);
  }

  @Bean
  public BarFilter barFilter() {
    return new BarFilter();
  }

  @Bean
  public PatternServiceRouteMapper serviceRouteMapper() {
    return new PatternServiceRouteMapper(
      "(?<name>^.+)",
      "${name}/");
  }
}
