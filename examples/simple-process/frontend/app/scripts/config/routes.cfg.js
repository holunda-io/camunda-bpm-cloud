module.exports = function ($stateProvider, $urlRouterProvider, $locationProvider) {
  'use strict';

  // For any unmatched url, redirect to /
  $urlRouterProvider.otherwise('/');

  // Now set up the states
  $stateProvider
    .state('default', {
      url: '/',
      template: '<h2>Wrong URL</h2>',
      controller: function () {
        console.log('Default called without formKey and taskId.');
      }
    })
    .state('task', {
      url: '/{formKey}?{taskId}',
      templateUrl: 'views/task.tpl.html',
      controller: 'TaskCtrl'
    });

};
