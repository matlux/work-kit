
## Install Splunk

     tar xvzf splunk-4.3.3-128297-Linux-x86_64.gz
     cd splunk/
     bin/splunk start --accept-license
     ps -alx | grep splunk
     bin/splunk stop
     bin/splunk start --accept-license



## How to

    ./bin/splunk list deploy-clients -auth admin:changeme1

## How to

    bin/splunk show config serverclass


## How to stop splunk server

    bin/splunk stop

## How to start splunk server

    bin/splunk start

## How to restart splunk server

    bin/splunk restart


## How to trigger re-deployment of scripts

    bin/splunk reload deploy-server

## How to check splunk logs

    less var/log/splunk/splunkd.log

