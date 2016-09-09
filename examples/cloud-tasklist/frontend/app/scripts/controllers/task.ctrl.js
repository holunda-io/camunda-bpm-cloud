'use strict';
module.exports = function ($scope, TaskService, ProcessService) {

/*
    function loadProcessDefinitions() {
        ProcessService.all().then(function (result) {
          $scope.processDefinitions = result.data;
          console.log('Retrieved process definitions ', $scope.processDefinitions);
        });
    }
*/
    function loadTasks() {
        TaskService.all().then(function (result) {
            $scope.tasks = result.data;
            console.log('Retrieved tasks ', $scope.tasks);
        });
    }

    function completeTask(task) {
        TaskService.complete(task).then(function () {
            loadTasks();
            $scope.currentTask = null;
        });
    }

    function setCurrentTask(task) {
        $scope.currentTask = task;
    }

    function isCurrentTask(task) {
        return $scope.currentTask !== null && $scope.currentTask.taskId === task.taskId && $scope.currentTask.formKey === task.formKey;
    }

/*
    function createProcessInstance(processDefinition) {
        ProcessService.createProcessInstance(processDefinition).then(function () {
            loadTasks();
        });
    }
*/
    // export
    $scope.processDefinitions = [];
    $scope.currentTask = null;

    $scope.isCurrentTask = isCurrentTask;
    $scope.setCurrentTask = setCurrentTask;
    $scope.completeTask = completeTask;

    // pre-load
    loadTasks();

};
