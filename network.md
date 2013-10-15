

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

