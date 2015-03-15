
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


## How to use cron on a Synology NAS

Unlike other Linux based systems, “crontab -e” won’t work on the Synology NAS.

Modifying crontab and enabling the deamon

1 Become root

    sudo -i

2 Edit /etc/crontab

    vim /etc/crontab

3 Restart the cron deamon by typing:

    synoservice -restart crond

Note: After a shutdown or reboot, you’ll need to restart the cron deamon to enable it again.

Running tasks using a different user account

If you want to run a task under a different user than root you can do the following:

```
#min    hour            mday    month   wday    who     command
30      0,6,12,18       *       *       *       root    /bin/su -c "/var
```

## How to Disable Password Authentication for SSH

```
vim /etc/ssh/sshd_config
```

modify the following:
```
ChallengeResponseAuthentication no
PasswordAuthentication no
UsePAM no
PubkeyAuthentication yes
AuthorizedKeysFile .ssh/authorized_keys
```
`PasswordAuthentication no` is the most important.

Once this is done, restart the SSH daemon to apply the settings:
```
/etc/init.d/sshd restart
```
see [this link](http://support.hostgator.com/articles/specialized-help/technical/how-to-disable-password-authentication-for-ssh) for more reference.

On Synology you can restart the daemon with:
Save the file and restart the SSH daemon. The easier is to use the GUI/WEB login. Click on the Control Panel -> Terminal. Uncheck Enable SSH Service, apply, check it again, and press apply again.

For Synology NAS follow [this](http://www.eldemonionegro.com/blog/archivos/2012/08/19/how-to-securely-activate-ssh-into-your-synology-diskstation-with-ssh-keys-and-no-root-login) for more details.

