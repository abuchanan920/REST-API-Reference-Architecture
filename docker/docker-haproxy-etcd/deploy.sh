#!/bin/bash
docker build -t hibu/haproxy-etcd .
docker tag hibu/haproxy-etcd $DOCKER_REGISTRY/hibu/haproxy-etcd
docker push $DOCKER_REGISTRY/hibu/haproxy-etcd