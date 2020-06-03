


## start local server from scratch

WIP

    minecraft_server.1.15.2.jar


## Launch my server onto Amazone EC2 (AWS)

Make sure you use a Ubuntu instance

```
scp -i ~/.ssh/<my key>.pem papa_minecraft_server.tar.gz ubuntu@ec2-54-190-197-35.us-west-2.compute.amazonaws.com:/home/ubuntu/
ssh -i ~/.ssh/<my key>.pem ubuntu@ec2-54-190-197-35.us-west-2.compute.amazonaws.com
sudo apt-get update
sudo apt install openjdk-8-jre-headless
tar xvfz papa_minecraft_server.tar.gz
cd papa
tmux new -s minecraft
java -Xmx1024M -Xms1024M -jar minecraft_server.1.15.2.jar nogui
```
