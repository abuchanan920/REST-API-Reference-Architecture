#!/bin/bash

etcdctl set /restreference/swaggerBasePath "https://rest"
etcdctl set /config/haproxy/restreference-api/HAPROXY_BACKEND_SERVICE "restreference-api"
etcdctl set /config/haproxy/restreference-api/HAPROXY_DEFAULTS_OPTION_HTTPCHK "HEAD \/healthcheck"
etcdctl set /config/haproxy/restreference-api/HAPROXY_HTTPCHK_PORT "8081"
etcdctl rm --recursive /data/restreference
