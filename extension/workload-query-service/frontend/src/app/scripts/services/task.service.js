module.exports = function taskService($http, EDGE_URI) {
  'use strict';

  return {
    all: all,
    complete: complete
  };

  function urlForTasks() {
    return EDGE_URI + '/tasks';
  }

  function urlForTaskComplete(task) {
    return EDGE_URI + '/tasks/' + task.taskId + '/complete';
  }

  function all() {
    return $http.get(urlForTasks());
  }

  function complete(task) {
    var url = urlForTaskComplete(task);
    console.log("completing task", url);
    return $http.post(url, '{}');
  }

};
