#!/bin/bash

docker-compose stop eventservice
mvn clean install -f extension/event-service/ -PdockerBuild
docker-compose up -d eventservice
docker logs -f camundabpmcloud_eventservice_1
