module.exports = function routing($stateProvider, $urlRouterProvider) {
  'use strict';

  // For any unmatched url, redirect to /
  $urlRouterProvider.otherwise('/');

  // Now set up the states
  $stateProvider
    .state('main', {
      url: '/',
      templateUrl: 'views/task.tpl.html',
      controller: 'TaskCtrl'
    });
};
