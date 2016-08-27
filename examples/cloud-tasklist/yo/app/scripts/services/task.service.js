
module.exports = function ($http, EDGE_URI) {
    'use strict';

    function urlForTasks() {
        return EDGE_URI + '/eventservice/task';
    }

    function urlForTaskComplete(task) {
        return EDGE_URI + '/' + task.engineId + '/rest/engine/default/task/' + task.taskId + '/complete';
    }

    function all() {
        return $http.get(urlForTasks());
    }

    function complete(task) {
        return $http.post(urlForTaskComplete(task), '{}');
    }

    return {
        all : all,
        complete: complete
    };
};
