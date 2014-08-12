#!/bin/bash

ETCD_PEER="10.1.42.1:4001"

/setup-haproxy.sh

exec /usr/local/bin/etcdctl --peers ${ETCD_PEER}  exec-watch --recursive ${HAPROXY_ETCD_CONFIG:-"/config"} -- /setup-haproxy.sh