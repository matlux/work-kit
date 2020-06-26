


## start local server from scratch

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