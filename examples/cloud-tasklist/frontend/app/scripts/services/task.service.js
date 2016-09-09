
module.exports = function ($http, EDGE_URI) {
    'use strict';

    function urlForTasks() {
        return EDGE_URI + '/task';
    }

    function urlForTaskComplete(task) {
        return EDGE_URI + '/task/' + task.taskId + '/complete';
    }

    function all() {
        return $http.get(urlForTasks());
    }

    function complete(task) {
    	var url = urlForTaskComplete(task);
    	console.log("completing task", url);
        return $http.post(url, '{}');
    }

    return {
        all : all,
        complete: complete
    };
};
