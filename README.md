# Camunda BPM Cloud

## How to run the example

1. Start EurekaServer

2. Start ConfigServer

3. Start EventService application

4. Start SimpleProcess and/or TrivialProcess apllication

5. Start external tasklist

6. Visit the [external tasklist](http:localhost:1338)

## Current status

### Start-up

* The EurekaServer starts up and serves as service-registry. You can visit its dashboard at http://localhost:8761/.
* When the ConfigServer is started, it registers itself as _CONFIGSERVER_ at EurekaServer.
* The EventService registers itself as _EVENTSERVICE_ at EurekaServer and provides .
    * a REST endpoint for the EventBroadcasters used in ProcessApplications (not yet discovarable via EurekaServer),
    * an in-memory TaskEventCache (currently a HashMap is used for the sake of simplicity) and
    * a REST endpoint for the external task list (stripped down camunda REST-API having one additional field _engineUrl_).
* When starting one of the example ProcessApplications, these register as _SIMPLEENGINE_/_TRIVIALENGINE_ at EurekaServer.
* The simple external task list queries the EventService for processDefinitions:
    * Therefore, the EventService fetches all ServiceInstances from EurekaServer and uses Metadata to identify all camunda engines.
    * For each camunda engine found, the EventService directly queries the engine's REST endpoint to retrieve all ProcessDefinitions.
    * The List of ProcessDefinitions is returned to the simple external task list.
* For each ProcessDefinition retrieved, the simple external task list provides a button to create an instance.

### Working with the simple external task lsit

* If an instance of a process is created, the SimpleProcess and/or TrivialProcess broadcast TaskEvents for every task that is created, completed or deleted to the EventService.
* The EventService caches the TaskEvents using its HashMap.
* The external tasklist queries the EventService for all cached TaskEvents.
* By clicking on a task, details of the task are shown and a complete button is present.
* When using the complete button, the task list sends a request to complete the task directly to the engine identified by the task.engineUrl field.
