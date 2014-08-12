#!/bin/bash

export RESTDEMOBASE=`pwd`
export PATH=$RESTDEMOBASE/vendor/etcdctl/bin:$RESTDEMOBASE/vendor/fleet/bin:$RESTDEMOBASE/sbin:$PATH

export DOCKER_HOST=172.17.8.100:4243
export ETCDCTL_PEERS="http://172.17.8.101:4001"
export FLEETCTL_ENDPOINT=http://172.17.8.101:4001