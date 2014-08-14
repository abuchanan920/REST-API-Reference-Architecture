#!/bin/bash

etcdctl set $CONFIG_CHANNEL/config/restreference/swaggerBasePath "https://rest"
etcdctl set $CONFIG_CHANNEL/config/haproxy/restreference-api/HAPROXY_BACKEND_SERVICE "$CONFIG_CHANNEL/services/restreference-api"
etcdctl set $CONFIG_CHANNEL/config/haproxy/restreference-api/HAPROXY_DEFAULTS_OPTION_HTTPCHK "HEAD \/healthcheck"
etcdctl set $CONFIG_CHANNEL/config/haproxy/restreference-api/HAPROXY_HTTPCHK_PORT "8081"
etcdctl rm --recursive $CONFIG_CHANNEL/data/restreference
