
## installing minishift

doc:
https://blog.novatec-gmbh.de/getting-started-minishift-openshift-origin-one-vm/

download:
https://github.com/minishift/minishift/releases

```bash
minishift start --vm-driver=virtualbox
```

## Login
```bash
oc login [url] --token=[....]
# or

oc login -u myusername [url]
```

## get list of contexts
```bash
oc config get-contexts
```

## create dev environment
```bash
oc config set-context dev
```

```bash
oc status
```
