I had the same issue on my Debian 3.2.51-1 x86_64. My mac address was 00:00:00:00:00:00 as well. My network was not working. However the driver seemed to be recognised by the kernel.

The difference between the hack above (musikmann) and mine was that my installation is a server (without UI installed) and NetworkManager is not installed. I had to follow a similar hack (slightly more complicated though) to get the NIC to work reliably after restart. I've really played with it for long time until I found the combination which worked (almost by luck). It seems that after restart, I had to force the NIC to be stopped, then changed network to DHCP setting (automatic IP); start/stop the network and change again to static IP + start network to get it to work. Another surprise was that I had to use "ifup eth0" command after restarting the network service to make this work. I've no idea why but it did solve my issue. 

Surprisingly DHCP would not work on restart of the box. Nor would the static ip setup on its own. It seems that forcing the NIC changes in the way I did had a healing effect on the driver behaviour... I'm not sure if I got the simplest solution but this definitely worked for me. I thought I would document this for other folks out there who would have to deal with the same ordeal.

So, to understand what I have done just follow the scripts below. The short explanation is that I had to modify my rc.local in which I would set a mac address (00:04:fe:11:22:33) and I would create 2 extra files (interface.dhcp and interface.static) which I would use to overwrite my /etc/network/interfaces between network restart. Just follow the script contents below:

-------------- content of rc.local
# cat /etc/rc.local 
#(skipped beginning of file ...)

/etc/init.d/networking stop
ifconfig eth0 hw ether 00:04:fe:11:22:33
cp /root/sky2hack/interface.dhcp /etc/network/interfaces

/etc/init.d/networking start
ifup eth0
/etc/init.d/networking stop
cp /root/sky2hack/interface.static /etc/network/interfaces
/etc/init.d/networking start
ifup eth0

-------- interface.dhcp file content
# cat /root/sky2hack/interface.dhcp
# This file describes the network interfaces available on your system
# and how to activate them. For more information, see interfaces(5).

# The loopback network interface
auto lo
iface lo inet loopback

# The primary network interface
allow-hotplug eth0
iface eth0 inet dhcp


--------- interface.static file content
# cat /root/sky2hack/interface.static
# This file describes the network interfaces available on your system
# and how to activate them. For more information, see interfaces(5).

# The loopback network interface
auto lo
iface lo inet loopback

# The primary network interface
allow-hotplug eth0
#iface eth0 inet dhcp
#auto eth0
iface eth0 inet static
address 192.168.77.10
netmask 255.255.255.0
gateway 192.168.77.18
dns-nameservers 192.168.77.18 8.8.8.8





-------------------------------------------------------------------------------------------------
------- FYI I've just past below a few details of my config to compare with yours -----
-------------------------------------------------------------------------------------------------
# dmesg | grep sky2
[ 0.566811] sky2: driver version 1.30
[ 0.566861] sky2 0000:01:00.0: setting latency timer to 64
[ 0.566890] sky2 0000:01:00.0: Yukon-2 EC Ultra chip revision 2
[ 0.566972] sky2 0000:01:00.0: irq 43 for MSI/MSI-X
[ 0.567366] sky2 0000:01:00.0: eth0: addr 00:00:00:00:00:00
[ 70.938042] sky2 0000:01:00.0: eth0: enabling interface
[ 73.476877] sky2 0000:01:00.0: eth0: Link is up at 1000 Mbps, full duplex, flow control rx
[ 89.973488] sky2 0000:01:00.0: eth0: disabling interface
[ 90.061559] sky2 0000:01:00.0: eth0: enabling interface
[ 93.222223] sky2 0000:01:00.0: eth0: Link is up at 1000 Mbps, full duplex, flow control rx

#lspci-v
01:00.0 Ethernet controller: Marvell Technology Group Ltd. 88E8055 PCI-E Gigabit Ethernet Controller
Subsystem: Marvell Technology Group Ltd. 88E8055 PCI-E Gigabit Ethernet Controller
Flags: bus master, fast devsel, latency 0, IRQ 43
Memory at fbefc000 (64-bit, non-prefetchable) [size=16K]
I/O ports at b800 [size=256]
Expansion ROM at fbec0000 [disabled] [size=128K]
Capabilities: [48] Power Management version 3
Capabilities: [50] Vital Product Data
Capabilities: [5c] MSI: Enable+ Count=1/1 Maskable- 64bit+
Capabilities: [e0] Express Legacy Endpoint, MSI 00
Capabilities: [100] Advanced Error Reporting
Kernel driver in use: sky2

# uname -a
Linux hypervizor 3.2.0-4-amd64 #1 SMP Debian 3.2.51-1 x86_64 GNU/Linux

# cat /etc/os-release
PRETTY_NAME="Debian GNU/Linux 7 (wheezy)"
NAME="Debian GNU/Linux"
VERSION_ID="7"
VERSION="7 (wheezy)"
ID=debian
ANSI_COLOR="1;31"
HOME_URL="skipped"
SUPPORT_URL="skipped"
BUG_REPORT_URL="skipped"
-----------

found under:
http://forums.fedoraforum.org/showthread.php?p=1438188#post1438188
