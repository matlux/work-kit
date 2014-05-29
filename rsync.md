

# Rsync



## How to test connection to rsync server

    rsync rsync://mathieu@thehost/backup


## How to create a rsync server

    apt-get install rsync
    create "/etc/rsyncd.conf"

put the following in it:

```sh
max connections = 1
log file = /var/log/rsync.log
timeout = 300
[cache]
comment = Cache of Mongrels
path = /usr/local/cache
read only = no
list = yes
uid = nobody
gid = nogroup
#auth users = mongrel
list = yes
hosts allow = 127.0.0.0/8 192.168.0.0/24
#secrets file = /etc/rsyncd.secrets 
```

create ''/etc/rsyncd.secret''
in the form of username:password ,  passwords are saved here as clear text. so don't forget to chmod it to 400 to keep the passwords somehow safe

make rsync to start as daemon:
edit ''/etc/inetd.conf'' and put the following line in the end:
rsync	stream	tcp	nowait	root	/usr/bin/rsync	rsync --daemon
voila, you are done, test your settings by typing:
rsync rsync://your_ip_or_domain_name/ 
this should list the modules
rsync rsync://your_ip_or_domain_name/public
this shuold list the files in your 'public' modules
rsync -avz rsync://your_ip_or_domain_name/public
synchronozing should start the transfer now.
don't forget to check the rsync man page to check what flags do you really need.

## reference

    http://www.linuxawy.org/node/12
