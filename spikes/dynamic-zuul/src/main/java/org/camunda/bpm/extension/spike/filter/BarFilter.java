package org.camunda.bpm.extension.spike.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class BarFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  @SneakyThrows
  public Object run() {

    final RequestContext ctx = RequestContext.getCurrentContext();
    final HttpServletRequest request = ctx.getRequest();

    log.info("{} request to {}", request.getMethod(), request.getRequestURL().toString());


    return null;
  }
}
