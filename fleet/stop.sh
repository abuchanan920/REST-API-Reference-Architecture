#!/bin/bash
pushd units
fleetctl stop *.service
popd