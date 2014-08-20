#!/bin/bash
pushd units
fleetctl destroy *.service
popd