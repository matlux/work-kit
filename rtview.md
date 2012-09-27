

## Install notes:

### Rtview Install (full with git)
    unzip install/rtv_ocm_5.8.0.1.zip
    cd rtvoc_5.8.0.1/
    git init
    git add *
     git commit -m "initial commit: rtview straight out from the zip"
    export JAVA_HOME=/tmp/deploy/jdk1.6.0_24
    export PATH=/tmp/deploy/jdk1.6.0_24/bin:$PATH
    . ./fixperms.sh
    git commit -m ". ./fixperms.sh" -a
    ./rtvoc_setup.sh
    git add rtvoc_info__.sh
    git commit -m "after running: ./rtvoc_setup.sh"
    cd conf/
    cp SampleCluster.properties UAT3.properties
    git add UAT3.properties
    git commit -m "added new cluster configuration: UAT3.properties"
    vim conf/UAT3.properties
    vim conf/coherence_setup.sh
    vim conf/ocm_setup.sh
    git commit -m "setup rtview for UAT3: to connect through direct connection and an OCMagent." -a

### Rtview Install (no git)

    unzip install/rtv_ocm_5.8.0.1.zip
    cd rtvoc_5.8.0.1/
    export JAVA_HOME=/tmp/deploy/
    export PATH=/tmp/deploy/jdk1.6.0_24/bin:$PATH
    . ./fixperms.sh
    ./rtvoc_setup.sh
    cd conf/
    cp SampleCluster.properties UAT3.properties
    vim conf/UAT3.properties
    vim conf/coherence_setup.sh
    vim conf/ocm_setup.sh

### Diffs:

UAT3.properties

    -tangosol.coherence.cluster=MyClusterName
    -tangosol.coherence.wka=
    +tangosol.coherence.cluster=MYCLUSTER-UAT3-47563
    +#tangosol.coherence.wka=
    -ocm.use_agent=false
    +ocm.use_agent=true
    -ocm.agent_log=MyClusterName_agent.log
    -ocm.data_log=MyClusterName_dataserver.log
    -ocm.display_log=MyClusterName_displayserver.log
    -ocm.historian_log=MyClusterName_historian.log
    +ocm.agent_log=HPCE-UAT3_agent.log
    +ocm.data_log=HPCE-UAT3_dataserver.log
    +ocm.display_log=HPCE-UAT3_displayserver.log
    +ocm.historian_log=HPCE-UAT3_historian.log
    
    -ocm.cachetotals_sub=-sub:$CACHETOTALS_TABLE:CACHETOTALS_MyClusterName
    -ocm.nodestats_sub=-sub:$NODESTATS_TABLE:NODESTATS_MyClusterName
    -ocm.alertdefs_sub=-sub:$ALERTDEFS_TABLE:ALERTDEFS_MyClusterName
    -ocm.alertdefs_tab_sub=-sub:$ALERTDEFS_TAB_TABLE:ALERTDEFS_TAB_MyClusterName
    +ocm.cachetotals_sub=-sub:$CACHETOTALS_TABLE:CACHETOTALS
    +ocm.nodestats_sub=-sub:$NODESTATS_TABLE:NODESTATS
    +ocm.alertdefs_sub=-sub:$ALERTDEFS_TABLE:ALERTDEFS
    +ocm.alertdefs_tab_sub=-sub:$ALERTDEFS_TAB_TABLE:ALERTDEFS_TAB
    -#tangosol.coherence.clusteraddress=
    +tangosol.coherence.clusteraddress=224.35.85.1
    -#tangosol.coherence.ttl=
    +tangosol.coherence.ttl=1

coherence_setup.sh

    -COHERENCE_CLASSPATH=~/coherence/lib/coh-352-patch-01.jar.jar:~/coherence/lib/coherence.jar
    +COHERENCE_CLASSPATH=/tmp/coherence/lib/coherence-3.7.1.jar

    -COHERENCE_CLUSTER=DemoCluster
    +COHERENCE_CLUSTER=UAT3

ocm_setup.sh

    RTV_USERPATH=$RTV_USERPATH:./ocsimdata.jar:$RTV_HOME/lib/ocsimdata.jar
    +RTV_USERPATH=$RTV_USERPATH:/tmp/deploy/calc-service/instance1/webapps/calculation-engine-services/WEB-INF/lib/*

## Install Notes Cluster with override file:

### Rtview Install (full with git)
    unzip install/rtv_ocm_5.8.0.1.zip
    cd rtvoc_5.8.0.1/
    git init
    git add *
     git commit -m "initial commit: rtview straight out from the zip"
    . ./fixperms.sh
    git commit -m ". ./fixperms.sh" -a
    ./rtvoc_setup.sh
    git add rtvoc_info__.sh
    git commit -m "after running: ./rtvoc_setup.sh"
    cd conf/
    cp SampleCluster.properties stg.properties
    git add stg.properties
    git commit -m "added new cluster configuration: stg.properties"
    vim conf/stg.properties
    vim conf/coherence_setup.sh
    vim conf/ocm_setup.sh
    git commit -m "setup rtview for stg: to connect through direct connection and an OCMagent." -a

### Diffs:

stg.properties

    -tangosol.coherence.cluster=MyClusterName
    -tangosol.coherence.wka=
    +#tangosol.coherence.cluster=MyClusterName
    +#tangosol.coherence.wka=
    -ocm.use_agent=false
    +ocm.use_agent=true
    -ocm.jmx_period=10000
    +ocm.jmx_period=300000
    -ocm.agent_log=MyClusterName_agent.log
    -ocm.data_log=MyClusterName_dataserver.log
    -ocm.display_log=MyClusterName_displayserver.log
    -ocm.historian_log=MyClusterName_historian.log
    +ocm.agent_log=STG_agent.log
    +ocm.data_log=STG_dataserver.log
    +ocm.display_log=STG_displayserver.log
    +ocm.historian_log=STG_historian.log

    -ocm.cachetotals_sub=-sub:$CACHETOTALS_TABLE:CACHETOTALS_MyClusterName
    -ocm.nodestats_sub=-sub:$NODESTATS_TABLE:NODESTATS_MyClusterName
    -ocm.alertdefs_sub=-sub:$ALERTDEFS_TABLE:ALERTDEFS_MyClusterName
    -ocm.alertdefs_tab_sub=-sub:$ALERTDEFS_TAB_TABLE:ALERTDEFS_TAB_MyClusterName
    +ocm.cachetotals_sub=-sub:$CACHETOTALS_TABLE:CACHETOTALS
    +ocm.nodestats_sub=-sub:$NODESTATS_TABLE:NODESTATS
    +ocm.alertdefs_sub=-sub:$ALERTDEFS_TABLE:ALERTDEFS
    +ocm.alertdefs_tab_sub=-sub:$ALERTDEFS_TAB_TABLE:ALERTDEFS_TAB

    -#tangosol.coherence.override=
    +tangosol.coherence.override=/config/coherence/coherence-override-UNIcast.xml


coherence_setup.sh

    -COHERENCE_CLASSPATH=~/coherence/lib/coh-352-patch-01.jar.jar:~/coherence/lib/coherence.jar
    +COHERENCE_CLASSPATH=/tmp/coherence/lib/coherence-3.7.1.jar

    -COHERENCE_CLUSTER=DemoCluster
    +COHERENCE_CLUSTER=UAT3

ocm_setup.sh

    RTV_USERPATH=$RTV_USERPATH:./ocsimdata.jar:$RTV_HOME/lib/ocsimdata.jar
    +RTV_USERPATH=$RTV_USERPATH:/apps/mrx/rtview/DGC/lib/*
    +RTV_USERPATH=$RTV_USERPATH:/NAS/sh-mrx_data-s/local_appsmrx/conf

# How to:

## Setup Apache server with OCM war file

    unzip install/apache-tomcat-6.0.18-sl.zip
    vim apache-tomcat-6.0.18-sl/conf/server.xml

## deploy OCM war file

    cd webconf/
    ./make_myocm_wars.sh
    cp myocm* /tmp/rtview/apache-tomcat-6.0.18-sl/webapps/

## update licence key

    rtview/lib/KEYS
    -5366618fac057b846038f5194013dfd0 # Expires 2012/6/1
    +5c96618d7c057b849838e01b6fa05fd1 # Expires 01-Sep-2012

## Move installation directory of rtvoc_5.8.0.1

    run ./rtvoc_setup.sh

  it does this created this file rtvoc_info__.sh

    +RTVOC_HOME=/apps/mrx/rtview/rtvoc_5.8.0.1
    +export RTVOC_HOME
    +RTV_HOME=/apps/mrx/rtview/rtvoc_5.8.0.1/rtview
    +export RTV_HOME


## Start OCM

    . ./rtvoc_init.sh
    Unix_RunOCMDatabase.sh -bg
    startocm.sh
    apache-tomcat-6.0.18-sl/bin/startup.sh


