#!/bin/bash

CONFCHANNEL=$1

rm -rf units
mkdir units

function processTemplate {
	TEMPLATE=$1
	COUNT=$2
	for i in `seq 1 $COUNT`; do
		UNITFILE=units/$TEMPLATE.$i.service
		cp templates/$TEMPLATE.template $UNITFILE
		sed -i "" -e "s|<CONFCHANNEL>|$CONFCHANNEL|g" $UNITFILE
		sed -i "" -e "s|<UNIT-ID>|$i|g" $UNITFILE
		sed -i "" -e "s|<DOCKER-REGISTRY>|$DOCKER_REGISTRY|g" $UNITFILE
	done
}

processTemplate logstash 1
processTemplate logstash-forwarder 3
processTemplate restreference-api 2
processTemplate restreference-api-announcer 2
processTemplate restreference-api-loadbalancer 1
