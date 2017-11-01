module.exports = function ($http, EDGE_URI) {
  'use strict';

  return {
    complete: complete,
    load: load
  };


  function complete(task) {
    var url = urlForTaskComplete(task);
    console.log("completing task", url);
    return $http.post(url, '{}');
  }

  function load(formKey, taskId) {
    return $http.get(urlForLoadTask(formKey, taskId));
  }


  function urlForTaskComplete(task) {
    return EDGE_URI + '/tasks/' + task.taskId + '/complete';
  }

  function urlForLoadTask(formKey, taskId) {
    return EDGE_URI + '/tasks/' + formKey + '/' + taskId;
  }

};
