'use strict';
module.exports = function taskController($scope, TaskService) {

  // export
  $scope.currentTask = null;

  $scope.isCurrentTask = isCurrentTask;
  $scope.setCurrentTask = setCurrentTask;
  $scope.completeTask = completeTask;

  // pre-load
  loadTasks();

  function loadTasks() {
    TaskService.all().then(function (result) {
        $scope.tasks = result.data;
        console.log('Retrieved tasks ', $scope.tasks);
    });
  }

  function completeTask(task) {
    TaskService.complete(task).then(function() {
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


};
