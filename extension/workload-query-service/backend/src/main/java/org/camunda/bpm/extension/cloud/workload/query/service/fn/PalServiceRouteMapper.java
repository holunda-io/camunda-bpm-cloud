package org.camunda.bpm.extension.cloud.workload.query.service.fn;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.stereotype.Component;

@Component
public class PalServiceRouteMapper extends PatternServiceRouteMapper {

  public static final String SERVICE_PATTERN = "(?<name>^.+)-(?<version>v.+$)";
  public static final String ROUTE_PATTERN = "${version}/${name}";

  public PalServiceRouteMapper() {
    super(SERVICE_PATTERN, ROUTE_PATTERN);
  }
}
