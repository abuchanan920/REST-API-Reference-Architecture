#!/bin/bash
rm -f ~/.fleetctl/known_hosts
pushd coreos
# Bring up the VMs
vagrant up
# Vagrant can't set docker containers to autostart in CoreOS yet, so manually reprovision...
vagrant provision docker-registry
# Set the config value to an initial value
sleep 20
etcdctl set /restreference/swaggerBasePath "https://rest"
# Add the key to the VMs so some of the command line tools work
ssh-add ~/.vagrant.d/insecure_private_key
../sbin/reapStaleContainers.sh
popd
