#!/bin/bash

set -e

usage() {
  echo -n "$(basename $0) [OPTION]...

This script starts up all backend containters of camunda bpm cloud.

 Options:
  -e, --elk    start additional ELK container collecting logs from cloud services


"
}

# read command line options
while [ "$1" != '' ];
  do
    case $1 in
      -h|--help) usage >&2; exit ;;
      -e|--elk)
        ELK=1
        ;;
        *)
        # unknown option
        ;;
    esac
    shift
done

# Determine, whether to start with or without ELK-Stack
if [[ ELK -eq 1 ]];
  then
    DOCKER_COMPOSE="docker-compose -f docker-compose.yml -f docker-compose.elk.yml"
  else
    DOCKER_COMPOSE="docker-compose"
fi

# Build the project and docker images
# mvn clean install

# Export the active docker machine IP
export DOCKER_IP=$(docker-machine ip $(docker-machine active))

# docker-machine doesn't exist in Linux, assign default ip if it's not set
DOCKER_IP=${DOCKER_IP:-0.0.0.0}

# Remove existing containers
#docker-compose stop
${DOCKER_COMPOSE} stop

#docker-compose rm -f
${DOCKER_COMPOSE}  rm -f

# Start the mysql service
docker-compose up -d mysql

sleep 5

# Start the discovery service next and wait
docker-compose up -d discovery

while [ -z ${DISCOVERY_SERVICE_READY} ]; do
  echo "Waiting for discovery..."
  if [ "$(curl --silent $DOCKER_IP:8761/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      DISCOVERY_SERVICE_READY=true;
  fi
  sleep 2
done


# Start the config service first and wait for it to become available
docker-compose up -d configserver

while [ -z ${CONFIG_SERVICE_READY} ]; do
  echo "Waiting for configserver..."
  if [ "$(curl --silent $DOCKER_IP:8888/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      CONFIG_SERVICE_READY=true;
  fi
  sleep 2
done


# Start the other containers
#docker-compose up -d
${DOCKER_COMPOSE} up -d

# Attach to the log output of the cluster
docker-compose logs
