#!/bin/bash

docker-compose stop workloadqueryservice
mvn clean install -f extension/workload-query-service/ -PdockerBuild
docker-compose up -d workloadqueryservice
docker logs -f camundabpmcloud_workloadqueryservice_1
