#!/bin/bash
for CORE in `seq 101 103`; do
	DOCKER_HOST=172.17.8.$CORE:4243
	for CONTAINER in `docker ps | cut -f 1 -d " " | grep -v CONTAINER`; do
		docker stop $CONTAINER
		docker rm $CONTAINER
	done
done
