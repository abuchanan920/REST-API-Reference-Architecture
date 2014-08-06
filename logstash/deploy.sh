#!/bin/bash
if [ ! -f certs/logstash-forwarder.crt ]; then
	pushd certs
	openssl req -x509 -batch -nodes -days 3650 -newkey rsa:2048 -keyout logstash-forwarder.key -out logstash-forwarder.crt -config openssl.cnf
	popd
fi
docker build -t hibu/logstash .
docker tag hibu/logstash 172.17.8.100:5000/hibu/logstash
docker push 172.17.8.100:5000/hibu/logstash
