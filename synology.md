
## How to install ipkg


You must know your CPU model to download right package (if its not DS112+). You can find useful information from [1](http://hoffmanns-cloud.de/synology/ipkg-auf-synology-installieren/) and [2](http://forum.synology.com/wiki/index.php/What_kind_of_CPU_does_my_NAS_have)

for the DS112j:

* ssh root@192.168.1.68 
* mkdir /volume/temp
* cd /volume/temp
* wget http://ipkg.nslu2-linux.org/feeds/optware/cs08q1armel/cross/stable/syno-mvkw-bootstrap_1.2-7_arm.xsh 
* chmod +x syno-mvkw-bootstrap_1.2-7_arm-ds111.xsh
* sh syno-mvkw-bootstrap_1.2-7_arm-ds111.xsh
* vi /root/.profile and put # before PATH ... 
* browser: reboot DS112j from Synology Diskstation Main menu

md5sum

    ef916b130e1fbf34906c7e920a822ef5 syno-mvkw-bootstrap_1.2-7_arm.xsh



see [this site](http://jaaknt.blogspot.co.uk/2012/10/how-to-install-ipkg-to-synology-ds112.html) for more info.
