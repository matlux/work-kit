
## install minecraft on ubuntu

ref: https://vitux.com/install-minecraft-on-ubuntu/

```
wget -o Minecraft.deb https://launcher.mojang.com/download/Minecraft.deb
sudo apt install gdebi-core
sudo gdebi Minecraft.deb
```

## start local server from scratch


ref: https://phoenixnap.com/kb/minecraft-dedicated-server

```
wget https://launcher.mojang.com/v1/objects/35139deedbd5182953cf1caa23835da59ca3d7cd/server.jar
mv server.jar minecraft_server.1.16.4.jar
tmux new -s minecraftsession
java -Xmx1024M -Xms1024M -jar minecraft_server.1.16.4.jar nogui
ctrl+b d
tmux a -t minecraftsession
```

WIP

    minecraft_server.1.15.2.jar


## Launch my server onto Amazone EC2 (AWS)

Make sure you use a Ubuntu instance

```
scp -i ~/.ssh/louismathieu.pem papa2.tar.gz ubuntu@54.188.10.70:/home/ubuntu/
ssh -i ~/.ssh/louismathieu.pem ubuntu@54.188.10.70
sudo apt-get update
sudo apt install openjdk-8-jre-headless
tar xvfz papa2.tar.gz
cd papa_1.15.2
tmux new -s minecraft
java -Xmx950M -Xms950M -jar minecraft_server.1.15.2.jar nogui
```
