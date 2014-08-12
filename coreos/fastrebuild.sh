#!/bin/bash
pushd coreos
vagrant destroy core-01 core-02 core-03
rm user-data.01 user-data.02 user-data.03
popd
./coreos/config-user-data.sh
pushd coreos
vagrant up
sleep 20
../sbin/configureEtcd.sh
popd
