#!/bin/bash
./createUnitFiles.sh $CONFIG_CHANNEL
pushd units
fleetctl submit *.service
popd