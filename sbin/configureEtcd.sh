#!/bin/bash

until etcdctl ls / >> /dev/null 2>&1; do echo "Waiting for etcd..."; sleep 5; done

etcdctl set $CONFIG_CHANNEL/config/restreference/swaggerBasePath "https://rest"
etcdctl set $CONFIG_CHANNEL/config/haproxy/restreference-api/HAPROXY_BACKEND_SERVICE "$CONFIG_CHANNEL/services/restreference-api"
etcdctl set $CONFIG_CHANNEL/config/haproxy/restreference-api/HAPROXY_DEFAULTS_OPTION_HTTPCHK "HEAD \/healthcheck"
etcdctl set $CONFIG_CHANNEL/config/haproxy/restreference-api/HAPROXY_HTTPCHK_PORT "8081"
etcdctl rm --recursive $CONFIG_CHANNEL/data/restreference >> /dev/null 2>&1
