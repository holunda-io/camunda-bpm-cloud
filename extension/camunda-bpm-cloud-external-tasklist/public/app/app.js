angular.module('SimpleCamundaClient', [])
    .constant('ENDPOINT_URI', 'http://localhost:8080/rest/engine/default/')
    .controller('TaskCtrl', function (TaskModel) {
        var main = this;

        function getTasks() {
            TaskModel.all()
                .then(function (result) {
                    main.tasks = result.data;
                });
        }

        function completeTask(task) {
            TaskModel.complete(task.taskId)
                .then(function (result) {
                    getTasks();
                });
        }

        function createProcessInstance(processDefinitionKey) {
            TaskModel.createProcessInstance(processDefinitionKey)
                .then(function (result) {
                    getTasks();
                });
        }

        function getProcessDefinitions() {
            TaskModel.getProcessDefinitions()
                .then(function (result) {
                    main.processDefinitions = result.data;
                });
        }

        main.tasks = [];
        main.getTasks = getTasks;
        main.completeTask = completeTask;
        main.processDefinitions = getProcessDefinitions;
        main.createProcessInstance = createProcessInstance;

        getTasks();
        getProcessDefinitions();
    })
    .service('TaskModel', function ($http, ENDPOINT_URI) {
        var service = this,
            path = 'http://localhost:8081/eventService/tasks';

        function getUrl() {
            return path;
        }

        function getUrlForId(taskId) {
            return ENDPOINT_URI + 'task/' + taskId;
        }

        function getUrlForProcessDefinitionKey(processDefinitionKey) {
            return ENDPOINT_URI + 'process-definition/key/' + processDefinitionKey;
        }

        function getUrlForProcessDefinitionKey(processDefinitionKey) {
            return ENDPOINT_URI + 'process-definition/key/' + processDefinitionKey;
        }

        function getUrlForInstanceCreation(processDefinitionKey) {
            return getUrlForProcessDefinitionKey(processDefinitionKey) + '/start';
        }

        function getUrlForProcessDefinitions() {
          return ENDPOINT_URI + 'process-definition/';
        }

        service.all = function () {
            return $http.get(getUrl());
        };

        service.fetch = function (taskId) {
            return $http.get(getUrlForId(taskId));
        };

        service.complete = function (taskId) {
            return $http.post(getUrlForId(taskId) + '/complete/', '{}');
        };

        service.createProcessInstance = function (processDefinitionKey) {
            return $http.post(getUrlForInstanceCreation(processDefinitionKey), '{}');
        };

        service.getProcessDefinitions = function () {
            return $http.get(getUrlForProcessDefinitions());
        };
    });
