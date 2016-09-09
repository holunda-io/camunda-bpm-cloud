# Camunda BPM Cloud

[![Travis CI](https://travis-ci.org/holisticon/camunda-bpm-cloud.svg?branch=master)](https://travis-ci.org/holisticon/camunda-bpm-cloud)

## Problem Statement

Process applications are following the BPM/SOA orchestration pattern invented many years ago. This promotes the separation of orchestration and the business functions. Since the existence of BPMN 2.0 standard, the orchestration is performed by the business process engine executing the BPMN 2.0 directly - which fosters the business-IT alignment. 

The bad part of the story is, that this pattern is implemented in the same way, as 20 years ago: as a monolith. In the early 90-ties the workflow engine (the predecessor of business process engines) were large installations hosted on big servers (fostered by big manufacturers). The were two reasonable runtime strategies: hosting business functions on the same machine as the business process engine, or deploying the business functions to other machines and strong distributed transactions to guarantee the consistency of the data. Both strategies result in a system thats runtime behaviour corresponds to those of a monolith, with all drawbacks and pains related to it operations.

In the ara of cloud computing, Microservices and self-contained systems those runtime strategies are not state of the art.

## Mission Statement

The goal of Camunda BPM Cloud project is to provide a implementation strategy of the BPM/SOA orchstration pattern that fosters louse coupling, dynamic scaling and resilience of the resulting system. In doing so, the following principles are applied:

- The orchestration layer (process engine with BPMN 2.0 model) must not have runtime dependencies to business/domain services (Usage of External Service Tasks)
- The high availability (HA) of the process engine can be reached by creating a homogenous cluster (clustered BPE, clustered shared DB, same deployments).
- The co-existence of several process engines must not rely on shared database.
- The user needs a single workload view, independing from the amount of process engine.

## Implementation Strategy

### Configuration and Registry
The engines are using Camunda BPM Spring in order to achieve maximum flexibility in ad-hoc deployment. In order to ease the management and configuration of the engines, a registry and a config server is used (from Spring Cloud project).

### Workload Service
In order to achive maximum decoupling from the engines to the business services and the task list application, a workload service should collect user and external service task from multiple engines. 

### Edge Service
In order to provide a single point of integration for a common task list (displaying the united workload of all process engines) an edge service should be provided.

### Runtime and Deployment strategy
In order to automate the runtime deployment docker shoould be used as a container for any component.


## How to run the example

Needs to be replaced by the start of Docker Compose:

1. Start EurekaServer - [http://localhost:8761/](http://localhost:8761/)
2. Start ConfigServer - [http://localhost:8888/](http://localhost:8888/)
3. Start EventService application
4. Start SimpleProcess and/or TrivialProcess apllication
5. Start external tasklist
6. Visit the [external tasklist](http:localhost:1338)

## Current status

### Start-up

We run all services in docker containers. To build the images, use the `dockerBuild` profile:

    mvn -PdockerBuild clean install

This will create docker images (prefix `camunda-bpm-cloud`) that you can check with `docker images`. (Hint old images 
will be "dangling", you can delete them via `docker images -f "dangling=true" -q | xargs docker rmi`).

**Run with Docker**

We all use native docker hosts on our machines, no need for additional VBox configurations. Start all containers with

* `-P` map all ports
* `--net="host"` use hosts network device directly, so 'localhost' works, [found here](http://stackoverflow.com/questions/29971909/use-eureka-despite-having-random-external-port-of-docker-containers)

#### Restarting a single container

    docker stop camundabpmcloud_eventservice_1
    mvn clean install -f extension/event-service/ -PdockerBuild
    docker-compose up -d eventservice
    docker logs -f camundabpmcloud_eventservice_1

#### service-discovery (eureka)

* The EurekaServer starts up and serves as service-registry. You can visit its dashboard at http://localhost:8761/.

```bash
docker run -P --net="host" camunda-bpm-cloud/camunda-bpm-cloud-service-registry
```

#### config-service (configuration)

* When the ConfigServer is started, it registers itself as _CONFIGSERVER_ at EurekaServer.

```bash
docker run -P --net="host" camunda-bpm-cloud/camunda-bpm-cloud-config-server
```

#### event service

* The EventService registers itself as _EVENTSERVICE_ at EurekaServer and provides .
    * a REST endpoint for the EventBroadcasters used in ProcessApplications (not yet discovarable via EurekaServer),
    * an in-memory TaskEventCache (currently a HashMap is used for the sake of simplicity) and
    * a REST endpoint for the external task list (stripped down camunda REST-API having one additional field _engineUrl_).
    
```bash
docker run -P --net="host" camunda-bpm-cloud/camunda-bpm-cloud-event-service
```

#### Example process applications

* When starting one of the example ProcessApplications, these register as _SIMPLEENGINE_/_TRIVIALENGINE_ at EurekaServer.
* The simple external task list queries the EventService for processDefinitions:
    * Therefore, the EventService fetches all ServiceInstances from EurekaServer and uses Metadata to identify all camunda engines.
    * For each camunda engine found, the EventService directly queries the engine's REST endpoint to retrieve all ProcessDefinitions.
    * The List of ProcessDefinitions is returned to the simple external task list.
* For each ProcessDefinition retrieved, the simple external task list provides a button to create an instance.

```bash
docker run -P --net="host" camunda-bpm-cloud/camunda-bpm-cloud-example-simple-process
docker run -P --net="host" camunda-bpm-cloud/camunda-bpm-cloud-example-trivial-process
```

### Working with the simple external task list

* If an instance of a process is created, the SimpleProcess and/or TrivialProcess broadcast TaskEvents for every task that is created, completed or deleted to the EventService.
* The EventService caches the TaskEvents using its HashMap.
* The external tasklist queries the EventService for all cached TaskEvents.
* By clicking on a task, details of the task are shown and a complete button is present.
* When using the complete button, the task list sends a request to complete the task directly to the engine identified by the task.engineUrl field.


## Resources

* [ASCII banner generator](http://www.network-science.de/ascii/), font: standard
* [Spring Cloud Config Server](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)
