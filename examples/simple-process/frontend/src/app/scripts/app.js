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
        return edgeUrl($location.absUrl(), $location.url());
      }
    };

    function edgeUrl(absoluteUrl, path) {
      console.debug('Absolute URL', absoluteUrl);
      console.debug('URL', path);
      return absoluteUrl.substring(0, absoluteUrl.length - path.length - 2) + '/api';
    }
  })
  .run(function (EDGE_URI, $location) {
    console.log('Application loaded with edge uri', EDGE_URI);
  })
// end
;
