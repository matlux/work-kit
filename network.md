

# iptables

## ref

    http://www.ibiblio.org/pub/linux/docs/howto/other-formats/html_single/Masquerading-Simple-HOWTO.html

## how to install iptables

    sudo apt-get install -y iptables-persistent
    
## how to change v4 rules on Debian 8

    sudo vim /etc/iptables/rules.v4

## How to clean all tables

    iptables -F; iptables -t nat -F; iptables -t mangle -F

## How to display NAT rules

    iptables -t nat -n -l


## How to masquerade

```sh
/sbin/iptables -P FORWARD ACCEPT
/sbin/iptables --table nat -A POSTROUTING -o eth0 -j MASQUERADE
```
or if you want a bit more efficient and you know what device to NAT internet traffic to:
```sh
/sbin/iptables -t nat -A POSTROUTING -o eth0 -j SNAT --to 192.168.99.253
```

Make sure that the ip forwarding is acutally enabled

    echo 1 > /proc/sys/net/ipv4/ip_forward

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

## Reference

https://help.ubuntu.com/lts/serverguide/openvpn.html

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


## Server installation on Ubuntu

    sudo apt-get install openvpn easy-rsa
    

## How to Setting up your own Certificate Authority (CA) and generating certificates and keys for an OpenVPN server and multiple clients

    mkdir /etc/openvpn/easy-rsa/
    cp -r /usr/share/easy-rsa/* /etc/openvpn/easy-rsa/


```sh
export KEY_COUNTRY="US"
export KEY_PROVINCE="NC"
export KEY_CITY="Winston-Salem"
export KEY_ORG="Example Company"
export KEY_EMAIL="steve@example.com"
export KEY_CN=MyVPN
export KEY_NAME=MyVPN
export KEY_OU=MyVPN
```

    cd /etc/openvpn/easy-rsa/
    source vars
    ./clean-all
    ./build-ca

```sh
cd /etc/openvpn/easy-rsa/
source vars
./clean-all
./build-ca
```

if error above on build-ca add following export to vars file:

    export KEY_ALTNAMES="something"
    
### Server Certificates

Next, we will generate a certificate and private key for the server:

    ./build-key-server myservername

As in the previous step, most parameters can be defaulted. Two other queries require positive responses, "Sign the certificate? [y/n]" and "1 out of 1 certificate requests certified, commit? [y/n]".

    ./build-dh
    

## Client Certificates

    cd /etc/openvpn/easy-rsa/
    source vars
    ./build-key client1
    
## 
    

