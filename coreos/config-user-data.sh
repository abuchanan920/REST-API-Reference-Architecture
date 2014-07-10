#!/bin/bash

ETCD=`curl https://discovery.etcd.io/new`

sed -e "s!\$TOKEN!$ETCD!g" coreos/user-data.template > coreos/user-data
