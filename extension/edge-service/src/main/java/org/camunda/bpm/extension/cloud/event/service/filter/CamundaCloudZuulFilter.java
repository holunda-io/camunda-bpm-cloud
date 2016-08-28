package org.camunda.bpm.extension.cloud.event.service.filter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pschalk on 26.08.16.
 *
 * @FIXME (PS) doesn't work ...
 */
@EnableEurekaClient
public class CamundaCloudZuulFilter extends ZuulFilter {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private UrlPathHelper pathHelper = new UrlPathHelper();

  @Autowired
  private EurekaClient discoveryClient;

  @Override
  public String filterType() {
    return "route";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return false; //TODO (PS) just disabled it because up to now its not working ...
  }

  @Override
  public Object run() {
    final RequestContext ctx = RequestContext.getCurrentContext();
    final HttpServletRequest request = ctx.getRequest();
    logger.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

    final String requestURI = this.pathHelper.getPathWithinApplication(ctx.getRequest());

    //Which engine is needed ...
    final String route = requestURI.split("/")[1];
    //Hostname of the engine ...
    final String engineHost = serviceUrl(route);
    //Needed path for engine ...
    final String appRequestURI = requestURI.substring(requestURI.indexOf("/",1)+1);

    final String forwardTo = engineHost+appRequestURI;

    logger.info("route to {}", forwardTo);

    ctx.put("requestURI", requestURI);
    ctx.set("forward.to", forwardTo);
    try {
      ctx.setRouteHost(new URL(forwardTo));
    } catch (MalformedURLException e) {
      logger.error("Geht was ned", e);
    }

    return null;
  }

  public String serviceUrl(String serverId) {
    logger.debug("Call Eureka with serverId {}", serverId);
    final InstanceInfo instance = discoveryClient.getNextServerFromEureka(serverId, false);
    return instance.getHomePageUrl();
  }
}
