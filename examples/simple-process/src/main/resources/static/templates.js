angular.module('simpleProcess').run(['$templateCache', function($templateCache) {
  'use strict';

  $templateCache.put('views/task.tpl.html',
    "<div class=\"col-md-8\"> <div class=\"panel panel-primary\"> <div class=\"panel-heading\">{{ taskpayload.name }}</div> <div class=\"panel-body\"> <form class=\"form-horizontal\" ng-submit=\"complete()\" name=\"simpleTaskForm\"> <div class=\"row\"> <div class=\"col-md-3\"> <label for=\"name\">Message</label> </div> <div class=\"col-md-9\"> <span>{{ taskpayload.value }}</span> </div> </div> <div class=\"row\"> <div class=\"col-md-3\"> <label for=\"name\">Name:</label> </div> <div class=\"col-md-9\"> <input ng-model=\"payload.name\" type=\"text\" id=\"name\" class=\"form-control\" placeholder=\"Enter your name\" ng-required=\"true\" maxlength=\"64\"> </div> </div> <div class=\"row\"> <div class=\"col-md-12\"> <div ng-show=\"error\" class=\"text-danger\"> <strong ng-bind=\"error\"></strong> </div> </div> </div> <div class=\"row\"> <div class=\"col-md-offset-3 col-md-9\"> <input type=\"submit\" class=\"btn btn-default btn-primary\" value=\"Complete\" name=\"button\" ng-disabled=\"simpleTaskForm.$invalid\"> <!--\n" +
    "            <a class=\"btn btn-secondary\" ui-sref=\"task({formKey: task.formKey, taskId: task.taskId})\"> Reload</a>\n" +
    "            --> </div> </div> </form> </div> </div> </div>"
  );

}]);
