#!/bin/bash
for CORE in `seq 101 103`; do
	DOCKER_HOST=172.17.8.$CORE:4243
	for IMAGE in `docker images|cut -f 1 -d " "|grep -v "REPOSITORY"|grep -v "<none>"`; do
		docker pull $IMAGE
	done
done
