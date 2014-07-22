#!/bin/bash
docker build -t hibu/haproxy-etcd .
docker tag hibu/haproxy-etcd 172.17.8.100:5000/hibu/haproxy-etcd
docker push 172.17.8.100:5000/hibu/haproxy-etcd
etcdctl --peers 172.17.8.101:4001 set /config/HAPROXY_DEFAULTS_OPTION_HTTPCHK "\/docs\/"