
# EON Envir 

## How to find it on the Rasberry Pi?

```
$ lsusb 
Bus 001 Device 002: ID 0424:9514 Standard Microsystems Corp. 
Bus 001 Device 001: ID 1d6b:0002 Linux Foundation 2.0 root hub
Bus 001 Device 003: ID 0424:ec00 Standard Microsystems Corp. 
Bus 001 Device 004: ID 067b:2303 Prolific Technology, Inc. PL2303 Serial Port
```

port is /dev/ttyUSB0

## How to setup the port



```
#not used:
#sudo modprobe usbserial vendor=0x067b product=0x2303

sudo stty -F /dev/ttyUSB0 cs8 57600 -parenb -cstopb raw
```

you can then read with

```
sudo cat /dev/ttyUSB0
```


