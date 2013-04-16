

# Symphony


## Deploy

```sh
. /opt/ego/profile.platform
egosh ego info

soamlogon -u user -x password
soamunreg [appName] -f
soamdeploy remove [appName] -c /consumer/tree
soamdeploy add [appName] -p package.zip -c /consumer/tree
soamreg appProfile.xml

soamlogoff
```

7.5.2	list available resources linked to a consumer

```sh
. ./load-password-uat.sh
soamlogon -u $SYM_USER -x $SYM_PASS
egosh resource group
egosh resource group compute-gpu
egosh resource group compute-cpu
egosh resource group ManagementHosts
```

## Startup On Linux

. profile.soam

soamstartup &

# Tips


## How to setup discrimination on Symphony

    select((!mg) && ('host1115070'))



