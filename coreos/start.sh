#!/bin/bash
rm -f ~/.fleetctl/known_hosts
pushd coreos
# Bring up the VMs
vagrant up
# Vagrant can't set docker containers to autostart in CoreOS yet, so manually reprovision...
vagrant provision docker-registry
# Add the key to the VMs so some of the command line tools work
ssh-add ~/.vagrant.d/insecure_private_key
# Set the config values to an initial settings
sleep 20
../sbin/configureEtcd.sh
# Remove old containers
../sbin/reapStaleContainers.sh
popd
