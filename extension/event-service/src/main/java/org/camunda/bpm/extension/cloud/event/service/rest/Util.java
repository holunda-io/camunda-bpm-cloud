package org.camunda.bpm.extension.cloud.event.service.rest;

import java.util.Collection;

import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Util {

  private Util() {

  }

  public static HttpEntity<String> toJson(Collection<?> collection) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<String>(new JSONArray(collection).toString(), headers);
  }

}
