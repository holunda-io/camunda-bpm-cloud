
module.exports = function ($http, EDGE_URI) {
    'use strict';

    function urlForProcessDefinitions() {
        return EDGE_URI + '/eventservice/process-definition';
    }

    function urlForInstanceCreation(processDefinition) {
        return EDGE_URI + '/' + processDefinition.engineId +'/rest/engine/default/process-definition/key/' + processDefinition.key + '/start';
    }

    function all() {
        return $http.get(urlForProcessDefinitions());
    }


    function createProcessInstance(processDefinition) {
        return $http.post(urlForInstanceCreation(processDefinition), '{}');
    }

    return {
        all : all,
        createProcessInstance : createProcessInstance
    };
};
