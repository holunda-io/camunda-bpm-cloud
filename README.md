# Camunda BPM Cloud

## How to run the example

1. Start SimpleProcess apllication

2. Start TrivialProcess application

3. Start EventService application

4. Start external tasklist

5. Visit the [external tasklist](http:localhost:1338)

## Current status

* The simple external tasklist queries the EventService for processDefinitions (currently a hardcoded list within the EventService).
* ProcessInstances of all processDefinitions found can be started via external task list.
* The camunda spring boot applications SimpleProcess and TrivialProcess send taskEvents in case a task is created, completed or deleted to the EventService.
* The EventService caches the taskEvents using a HashMap.
* The external tasklist queries the EventService for all broadcasted events.
* By clicking on a task, details of the task are shown and a complete button is present.
* When using the complete button, the tasklist sends a complete request directly to the engine identified by the task.engineUrl field.
