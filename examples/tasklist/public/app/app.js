angular.module('SimpleCamundaClient', [])
    .constant('EVENT_SERVICE_URI', 'http://localhost:8081/eventService/')
    .constant('ENGINE_URI', 'http://localhost:8080/rest/engine/default/')
    .controller('TaskCtrl', function (TaskModel) {
        var main = this;

        function getTasks() {
            TaskModel.all()
                .then(function (result) {
                    getProcessDefinitions();
                    main.tasks = result.data;
                });
        }

        function completeTask(task) {
            TaskModel.complete(task)
                .then(function (result) {
                    getTasks();
                    main.currentTask = null;
                });
        }

        function setCurrentTask(task) {
            main.currentTask = task;
        }

        function isCurrentTask(taskId, formKey) {
            return main.currentTask !== null && main.currentTask.taskId === taskId && main.currentTask.formKey === formKey;
        }

        function createProcessInstance(processDefinition) {
            TaskModel.createProcessInstance(processDefinition)
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
        main.currentTask = null;
        main.setCurrentTask = setCurrentTask;
        main.isCurrentTask = isCurrentTask;
        main.completeTask = completeTask;
        main.processDefinitions = getProcessDefinitions;
        main.createProcessInstance = createProcessInstance;

        getProcessDefinitions();
        getTasks();
    })
    .service('TaskModel', function ($http, EVENT_SERVICE_URI, ENGINE_URI) {
        var service = this;

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

        function getUrlForProcessDefinition(processDefinition) {
            return processDefinition.engineUrl + 'rest/engine/default/process-definition/key/' + processDefinition.key;
        }

        function getUrlForInstanceCreation(processDefinition) {
            return getUrlForProcessDefinition(processDefinition) + '/start';
        }

        function getUrlForProcessDefinitions() {
          return getEventServiceUrl() + 'process-definition';
        }

        service.all = function () {
            return $http.get(getEventServiceUrl() + 'task');
        };

        service.fetch = function (task) {
            return $http.get(getUrlForTask(task));
        };

        service.complete = function (task) {
            return $http.post(getUrlForTaskComplete(task), '{}');
        };

        service.createProcessInstance = function (processDefinition) {
            return $http.post(getUrlForInstanceCreation(processDefinition), '{}');
        };

        service.getProcessDefinitions = function () {
            return $http.get(getUrlForProcessDefinitions());
        };
    });