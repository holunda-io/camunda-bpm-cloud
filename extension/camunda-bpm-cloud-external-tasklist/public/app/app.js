angular.module('SimpleCamundaClient', [])
    .constant('EVENT_SERVICE_URI', 'http://localhost:8081/eventService/tasks')
    .constant('ENGINE_URI', 'http://localhost:8080/rest/engine/default/')
    .controller('TaskCtrl', function (TaskModel) {
        var main = this;

        function getTasks() {
            TaskModel.all()
                .then(function (result) {
                    main.tasks = result.data;
                });
        }

        function completeTask(task) {
            TaskModel.complete(task)
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
    .service('TaskModel', function ($http, EVENT_SERVICE_URI, ENGINE_URI) {
        var service = this,
            path = 'http://localhost:8081/eventService/tasks';

        function getEventServiceUrl() {
            return EVENT_SERVICE_URI;
        }

        function getEngineUrl() {
          return ENGINE_URI;
        }

        function getUrlForTask(task) {
          return task.engineUrl + 'rest/engine/default/task/' + task.taskId;
        }

        function getUrlForTaskComplete(task) {
          return getUrlForTask(task)  + '/complete/';
        }

        function getUrlForProcessDefinitionKey(processDefinitionKey) {
            return getEngineUrl() + 'process-definition/key/' + processDefinitionKey;
        }

        function getUrlForProcessDefinitionKey(processDefinitionKey) {
            return getEngineUrl() + 'process-definition/key/' + processDefinitionKey;
        }

        function getUrlForInstanceCreation(processDefinitionKey) {
            return getUrlForProcessDefinitionKey(processDefinitionKey) + '/start';
        }

        function getUrlForProcessDefinitions() {
          return getEngineUrl() + 'process-definition/';
        }

        service.all = function () {
            return $http.get(getEventServiceUrl());
        };

        service.fetch = function (taskId) {
            return $http.get(getUrlForId(taskId));
        };

        service.complete = function (task) {
            return $http.post(getUrlForTaskComplete(task), '{}');
        };

        service.createProcessInstance = function (processDefinitionKey) {
            return $http.post(getUrlForInstanceCreation(processDefinitionKey), '{}');
        };

        service.getProcessDefinitions = function () {
            return $http.get(getUrlForProcessDefinitions());
        };
    });
