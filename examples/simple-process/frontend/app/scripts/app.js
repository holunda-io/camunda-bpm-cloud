'use strict';
const angular = require('angular');
require('angular-ui-router');
require('angular-bootstrap-npm');
require('angular-utils-pagination');
window.$ = window.jQuery = require('jquery');
const _ = require('lodash');

angular
  .module('simpleProcess', [
    'ui.router',
    'ui.bootstrap'
  ])
  //attach routes
  .config(require('./config/routes.cfg.js'))
  // controllers
  .controller('TaskCtrl', require('./controllers/task.ctrl.js'))
  // services
  .service('TaskService', require('./services/task.service.js'))

  .provider('EDGE_URI', function () {
    return {
      $get: function ($location) {
        var url = $location.absUrl().substring(0, $location.absUrl().length - $location.path().length) + '/api';
        return url;
      }
    };
  })
  .run(function (EDGE_URI) {
    console.log('Application loaded with edge uri', EDGE_URI);
  })

// end
;
