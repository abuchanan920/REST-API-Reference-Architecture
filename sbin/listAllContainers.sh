#!/bin/bash
for CORE in `seq 1 3`; do
	echo "Core-0$CORE"
	DOCKER_HOST=172.17.8.10$CORE:4243 docker ps -a
	echo 
done
