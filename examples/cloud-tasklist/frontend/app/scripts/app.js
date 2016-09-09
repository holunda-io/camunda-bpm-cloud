'use strict';
const angular = require('angular');
require('angular-ui-router');
require('angular-bootstrap-npm');
require('angular-utils-pagination');
window.$ = window.jQuery = require('jquery');
const _ = require('lodash');

angular
  .module('cloudTasklistApp', [
    'ui.router',
    'ui.bootstrap',
    'angularUtils.directives.dirPagination'
  ])
  //attach routes
  .config(require('./config/routes.cfg.js'))
  // controllers
  .controller('TaskCtrl', require('./controllers/task.ctrl.js'))
  // services
  .service('TaskService', require('./services/task.service.js'))
  .service('ProcessService', require('./services/process.service.js'))
  // constants
  .constant('EDGE_URI', '/cloud')

  // end
;
