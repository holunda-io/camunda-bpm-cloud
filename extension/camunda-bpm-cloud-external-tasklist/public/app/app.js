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
            TaskModel.complete(task.id)
                .then(function (result) {
                    getTasks();
                });
        }

        function createProcessInstance() {
            TaskModel.createProcessInstance('SimpleProcess')
                .then(function (result) {
                    getTasks();
                });
        }

        main.tasks = [];
        main.getTasks = getTasks;
        main.completeTask = completeTask;
        main.createProcessInstance = createProcessInstance;

        getTasks();
    })
    .service('TaskModel', function ($http, ENDPOINT_URI) {
        var service = this,
            path = 'task/';

        function getUrl() {
            return ENDPOINT_URI + path;
        }

        function getUrlForId(taskId) {
            return getUrl(path) + taskId;
        }

        function getUrlForProcessDefinitionKey(processDefinitionKey) {
            return ENDPOINT_URI + 'process-definition/key/' + processDefinitionKey;
        }

        function getUrlForInstanceCreation(processDefinitionKey) {
            return getUrlForProcessDefinitionKey(processDefinitionKey) + '/start';
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

    });
