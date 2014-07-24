#!/bin/bash
docker build -t hibu/haproxy-etcd .
docker tag hibu/haproxy-etcd 172.17.8.100:5000/hibu/haproxy-etcd
docker push 172.17.8.100:5000/hibu/haproxy-etcd
etcdctl --peers 172.17.8.101:4001 set /config/haproxy/restreference-api/HAPROXY_BACKEND_SERVICE "restreference-api"
etcdctl --peers 172.17.8.101:4001 set /config/haproxy/restreference-api/HAPROXY_DEFAULTS_OPTION_HTTPCHK "HEAD \/healthcheck"
etcdctl --peers 172.17.8.101:4001 set /config/haproxy/restreference-api/HAPROXY_HTTPCHK_PORT "8081"
