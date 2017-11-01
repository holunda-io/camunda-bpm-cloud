'use strict';
module.exports = function ($scope, $stateParams, TaskService) {


  var formKey;
  var taskId;

  if ($stateParams.formKey) {
    formKey = $stateParams.formKey;
  }

  if ($stateParams.taskId) {
    taskId = $stateParams.taskId;
  }


  if (formKey && taskId) {
    TaskService.load(formKey, taskId).then(function success(response) {
      console.log(response);
      $scope.payload = response.data;
    }, function error() {

    });
  } else {
    console.log("Wring params", $stateParams);
  }

  function complete() {
    TaskService.complete('');
  }

};
