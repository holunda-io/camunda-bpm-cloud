'use strict';
const angular = require('angular');
require('angular-ui-router');
require('angular-bootstrap-npm');
window.$ = window.jQuery = require('jquery');
const _ = require('lodash');

angular
  .module('cloudTasklistApp', [
    'ui.router',
    'ui.bootstrap'
  ])
  //attach routes
  .config(require('./config/routes.cfg.js'))
  // controllers
  .controller('TaskCtrl', require('./controllers/task.ctrl.js'))
  // services
  .service('TaskService', require('./services/task.service.js'))
  .service('ProcessService', require('./services/process.service.js'))
  // constants
  .constant('EDGE_URI', '')

  // end
;
