package org.camunda.bpm.extension.cloud.workload.query.service.filter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZuulFilterTest {

  MarkCompletedZuulFilter filter = new MarkCompletedZuulFilter(null, null);


  @Test
  public void testCandidates() {
    assertThat(this.filter.collectTaskIdCandidates("/simple/what/ever", "param1=bar&param2=foo")).containsOnly("simple", "what", "ever", "bar", "foo");
    assertThat(this.filter.collectTaskIdCandidates("/simple/what/ever", "param1=bar&param2")).containsOnly("simple", "what", "ever", "bar");
    assertThat(this.filter.collectTaskIdCandidates("/simple/what/ever", "==")).containsOnly("simple", "what", "ever");
    assertThat(this.filter.collectTaskIdCandidates("/simple/what/ever", "")).containsOnly("simple", "what", "ever");
    assertThat(this.filter.collectTaskIdCandidates("/simple/what/ever", null)).containsOnly("simple", "what", "ever");
  }


}
