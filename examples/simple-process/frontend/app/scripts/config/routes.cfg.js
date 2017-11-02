module.exports = function ($stateProvider, $urlRouterProvider, $locationProvider) {
  'use strict';

  // For any unmatched url, redirect to /
  $urlRouterProvider.otherwise('/');

  // Now set up the states
  $stateProvider
    .state('bad-url', {
      url: '/',
      template: '<h2>Wrong URL</h2>',
      controller: function () {
      }
    })
    .state('task', {
      url: '/{formKey}?{taskId}',
      templateUrl: 'views/task.tpl.html',
      controller: 'TaskCtrl'
    });

};
