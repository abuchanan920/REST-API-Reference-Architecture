#!/bin/bash
for CORE in `seq 101 103`; do
	DOCKER_HOST=172.17.8.$CORE:4243
	for CONTAINER in `DOCKER_HOST=$DOCKER_HOST docker ps -a| cut -f 1 -d " " | grep -v CONTAINER`; do
		echo "Stopping $CONTAINER... on $DOCKER_HOST"
		docker stop $CONTAINER
		docker rm $CONTAINER
	done
done
