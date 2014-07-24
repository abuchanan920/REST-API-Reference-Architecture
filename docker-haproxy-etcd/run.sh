#!/bin/bash
/setup-haproxy.sh
exec supervisord -n -c /etc/supervisord.conf
