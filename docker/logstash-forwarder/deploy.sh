#!/bin/bash
if [ ! -d certs ]; then
	cp -a ../logstash/certs .
fi
docker build -t hibu/logstash-forwarder .
docker tag hibu/logstash-forwarder 172.17.8.100:5000/hibu/logstash-forwarder
docker push 172.17.8.100:5000/hibu/logstash-forwarder
