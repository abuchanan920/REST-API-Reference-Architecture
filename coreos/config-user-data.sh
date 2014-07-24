#!/bin/bash

ETCD=`curl https://discovery.etcd.io/new`

for CORE in `seq -f %02g 1 3`; do
	sed -e "s!\$TOKEN!$ETCD!g" coreos/user-data.template | sed -e "s!\$CORE!$CORE!g" > coreos/user-data.$CORE
done