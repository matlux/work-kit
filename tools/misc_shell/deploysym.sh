#!/bin/sh

. /opt/ego/profile.platform

MOUNT=/data/chefArtifacts

. $MOUNT/conf/symphony/load-password.sh

soamlogon -u $SYM_USER -x $SYM_PASS

soamunreg MYAPP -f

soamdeploy remove MYAPP -c /consumertree/gpu
soamdeploy add MYAPP -p $MOUNT/target/MYAPP.zip -c /consumertree/gpu
soamreg $MOUNT/target/MYAPP.xml

soamlogoff
