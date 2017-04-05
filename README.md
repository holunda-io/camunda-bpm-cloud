<img src="https://www.holisticon.de/wp-content/uploads/2013/05/holisticon-logo-hamburg.gif" align="right" />
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

## Runtime and Deployment strategy
In order to automate the runtime deployment docker shoould be used as a container for any component.

### Requirements

You would need the following software in your environment to run the example:

  * Java 8
  * Maven 3.3
  * Docker 1.11 (native or via VirtualBox)
  * Docker Compose 1.7.1
  * npm >= 3.9.6
  * node 4.4.3

### Start-up

We run all services in docker containers. To build the images, use the `dockerBuild` profile:

    mvn -PdockerBuild clean install

This will create docker images (prefix `camunda-bpm-cloud`) that you can check with `docker images`. (Hint old images will be "dangling", you can delete them via `docker images -f "dangling=true" -q | xargs docker rmi`).

The containers are configured using `docker-compose`, but they have to be started in a certain order. In order to simplify the start, the run script is provided. Just call from console:

    ./run.sh

This will start the services:

* discovery
* config
* edge
* workloadservice
* simpleprocess
* trivialprocess

In order to start the Cloud Tasklist please run:

     ./tasklist.sh
 

**Runing with Docker**

If you use native docker hosts on our machines, no need for additional VBox configurations is required. If you run docker based on VirtualBox, please make sure to expose the IP address of the Docker host by exeucting the following line of code: `export DOCKER_IP=$(docker-machine ip $(docker-machine active))
`

Start all containers with

* `-P` map all ports
* `--net="host"` use hosts network device directly, so `localhost` works fine, [found here](http://stackoverflow.com/questions/29971909/use-eureka-despite-having-random-external-port-of-docker-containers)

**Restarting a single container**

    docker-compose stop camundabpmcloud_workloadservice_1
    mvn clean install -f extension/workload-service/ -PdockerBuild
    docker-compose up -d workloadservice
    docker logs -f camundabpmcloud_workloadservice_1

## Components

### Service-discovery / Registry (eureka)

The EurekaServer starts up and serves as service-registry.

### Config-service (configuration)

When the ConfigServer is started, it registers itself as _CONFIGSERVER_ at EurekaServer.

### Workload service

The WorkloadService registers itself as _WORKLOADSERVICE_ at EurekaServer and provides

* a REST endpoint for the EventBroadcasters used in ProcessApplications,
* an in-memory TaskQueryObjectCache (currently a HashMap is used for the sake of simplicity) and
* a REST endpoint for the external task list (stripped down camunda REST-API having one additional field _engineUrl_).

#### Internal structure of the workload service

The workload service is implemented using the Axon framework.

![Details of the workload-service](./docs/images/workload-service.png "Details of the workload-service")
    
### Edge Service (zuul)

The edge service acts as a reverse proxy to access the workload service from outside of the cloud.

### Cloud Tasklist

The Cloud task list is a SpringBoot Application containing a task list and a component which connects to the Camunda cloud vias edge service. 


## Example Scenarios

* If an instance of a process is created, the SimpleProcess and/or TrivialProcess broadcast TaskEvents for every task that is created, completed or deleted to the WorkloadService.
* The WorkloadService caches the Tasks using its HashMap.
* The Cloud tasklist queries the WorkloadService for all cached Tasks.
* By clicking on a task, details of the task are shown and a complete button is present.
* When using the complete button, the task list sends a request to complete the task directly to the engine identified by the task.engineUrl field.


## Resources

* [ASCII banner generator](http://www.network-science.de/ascii/), font: standard
* [Spring Cloud Config Server](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)
