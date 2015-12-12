

# iptables

## ref

    http://www.ibiblio.org/pub/linux/docs/howto/other-formats/html_single/Masquerading-Simple-HOWTO.html

## How to clean all tables

    iptables -F; iptables -t nat -F; iptables -t mangle -F

## How to display NAT rules

    iptables -t nat -n -l


## How to masquerade

```sh
/sbin/iptables -P FORWARD ACCEPT
/sbin/iptables --table nat -A POSTROUTING -o eth0 -j MASQUERADE
```
or
```sh
/sbin/iptables -t nat -A POSTROUTING -o eth0 -j SNAT --to 192.168.99.253
```

## How to forward a port

```sh
/sbin/iptables -t nat -A PREROUTING -i eth0 -p tcp -d 192.168.98.253 --dport 51413 -j DNAT --to 192.168.76.15:51413
/sbin/iptables -A FORWARD -p tcp -i eth0 -d 192.168.76.15 --dport 51413 -j ACCEPT
```

# routes

## How to add a static route

```sh
# your route command here
/sbin/route add -net 192.168.2.0 netmask 255.255.255.0 gw 192.168.0.228
```

on Mac:

sudo route -n add 192.168.2.0/24 192.168.0.228

# Network speed test

## Server
nc -v -l 2222 > /dev/null

## Client
dd if=/dev/zero bs=1024000 count=512 | nc -v $IP_ADDR_OF_SERVER 2222


# How to install DHCP server

```sh
root# apt-get install isc-dhcp-server
```

edit `/etc/default/isc-dhcp-server`

```sh
INTERFACES="eth0"
```

Edit

```sh
user> dhcpd.conf 
```

## Starting the DHCP server

You can test your DHCP server without rebooting:

```sh
sudo service isc-dhcp-server stop
sudo service isc-dhcp-server start
sudo ifdown eth0
sudo ifup eth0
```

To see your DHCP server error messages, or see when a device has grabbed an IP from this DHCP server:

```sh
sudo tail /var/log/syslog
```

# OpenVPN

## files


|Filename	|Needed By	|Purpose	|Secret|
|-----------|-----------|-----------|------|
|ca.crt	|server + all clients	|Root CA certificate	|NO|
|ca.key	|key signing machine only	|Root CA key	|YES|
|dh{n}.pem	|server only	|Diffie Hellman parameters	|NO|
|server.crt	|server only	|Server Certificate	|NO|
|server.key	|server only	|Server Key	|YES|
|client1.crt	|client1 only	|Client1 Certificate	|NO|
|client1.key	|client1 only	|Client1 Key	|YES|
|client2.crt	|client2 only	|Client2 Certificate	|NO|
|client2.key	|client2 only	|Client2 Key	|YES|
|client3.crt	|client3 only	|Client3 Certificate	|NO|
|client3.key	|client3 only	|Client3 Key	|YES|



