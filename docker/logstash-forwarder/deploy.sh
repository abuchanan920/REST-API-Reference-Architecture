#!/bin/bash
if [ ! -d certs ]; then
	cp -a ../logstash/certs .
fi
docker build -t hibu/logstash-forwarder .
docker tag hibu/logstash-forwarder $DOCKER_REGISTRY/hibu/logstash-forwarder
docker push $DOCKER_REGISTRY/hibu/logstash-forwarder
