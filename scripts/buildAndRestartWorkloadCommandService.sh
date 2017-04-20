#!/bin/bash

docker-compose stop workloadcommandservice
mvn clean install -f extension/workload-command-service/ -PdockerBuild
docker-compose up -d workloadcommandservice
docker logs -f camundabpmcloud_workloadcommandservice_1
