#!/bin/bash
for CORE in `seq 101 103`; do
	DOCKER_HOST=172.17.8.$CORE:4243
	for CONTAINER in `docker ps -a | grep Exited|cut -f 1 -d " "`; do
		docker rm $CONTAINER
	done
done
