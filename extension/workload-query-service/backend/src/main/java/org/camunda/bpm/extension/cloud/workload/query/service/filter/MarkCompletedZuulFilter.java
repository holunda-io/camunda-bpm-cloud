package org.camunda.bpm.extension.cloud.workload.query.service.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.extension.cloud.workload.command.MarkTaskForCompletionCommand;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObject;
import org.camunda.bpm.extension.cloud.workload.query.service.model.TaskQueryObjectRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class MarkCompletedZuulFilter extends ZuulFilter {

  private final TaskQueryObjectRepository repository;
  private final MarkCompletedCommand command;

  public MarkCompletedZuulFilter(final TaskQueryObjectRepository repository, final MarkCompletedCommand command) {
    this.repository = repository;
    this.command = command;
  }

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    final RequestContext ctx = RequestContext.getCurrentContext();
    final HttpServletRequest request = ctx.getRequest();
    return request.getMethod().equals("POST");
  }

  @Override
  public Object run() {
    final RequestContext ctx = RequestContext.getCurrentContext();
    final HttpServletRequest request = ctx.getRequest();

   /*
    * determine task id candidates
    * there are different ways to do so, we collect all path segments
    * and all values of query parameters if any.
    *
    * possible alternative is to send the task id as a header
    * -> requirement on process application as a URI convention
    * -> requirement on process application as JS header in submit
    */

    final Set<String> candidates = collectTaskIdCandidates(request.getServletPath(), request.getQueryString());
    candidates.stream().forEach(c -> markCompleted(c));

    return null;
  }

  /**
   * Mark as completed
   *
   * @param taskId
   */
  public void markCompleted(final String taskId) {

    final TaskQueryObject one = this.repository.findOne(taskId);
    if (one != null) {
      /*
      // this is a hack that modifies the view
      one.setEventType(TaskQueryObjectStateEnum.PENDING_TO_COMPLETE);
      */

      // sends a command via RabbitMQ
      this.command.accept(new MarkTaskForCompletionCommand(one.getTaskId()));
      log.info("Task {} marked to pending", one.getTaskId());
    }
  }


  /**
   * Parses request uri and extracts candidates for task id.
   *
   * @param servletPath HTTP request servlet path.
   * @param queryString HTTP request query string.
   * @return set of task id candidates.
   */
  Set<String> collectTaskIdCandidates(final String servletPath, final String queryString) {
    final Set<String> result = new HashSet<>();

    if (servletPath != null) {
      result.addAll(Arrays.stream(servletPath.split("/")).filter(s -> s != null && !s.trim().isEmpty()).map(s -> s.trim()).collect(Collectors.toList()));
    }

    if (queryString != null) {
      result.addAll(Arrays.stream(queryString.split("&"))
        .filter(s -> s != null && !s.trim().isEmpty() && s.contains("="))
        .map(s -> s.split("="))
        .filter(keyValue -> keyValue != null && keyValue.length == 2)
        .map(keyValue -> keyValue[1])
        .collect(Collectors.toList()));
    }

    return result;
  }

}
