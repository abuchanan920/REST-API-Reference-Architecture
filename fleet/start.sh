#!/bin/bash
./createUnitFiles.sh $CONFIG_CHANNEL
pushd units
fleetctl start *.service
popd