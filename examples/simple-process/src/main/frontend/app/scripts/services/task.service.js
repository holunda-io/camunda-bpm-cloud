module.exports = function ($http, EDGE_URI) {
  'use strict';

  return {
    complete: complete,
    load: load
  };


  function complete(formKey, taskId, payload) {
    var url = taskUrl(formKey, taskId);
    console.log("Completing task", formKey, url);
    return $http.post(url, payload);
  }

  function load(formKey, taskId) {
    return $http.get(taskUrl(formKey, taskId));
  }


  function taskUrl(formKey, taskId) {
    return EDGE_URI + '/tasks/' + formKey + '/' + taskId;
  }

};
