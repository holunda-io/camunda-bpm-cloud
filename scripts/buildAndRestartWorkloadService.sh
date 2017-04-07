#!/bin/bash

docker-compose stop workloadservice
mvn clean install -f extension/workload-service/ -PdockerBuild
docker-compose up -d workloadservice
docker logs -f camundabpmcloud_workloadservice_1
