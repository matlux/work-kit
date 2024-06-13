
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

### Where to find the latest version of the server

https://www.minecraft.net/en-us/download/server/


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


## Minecraft


### list item

ref: https://www.digminecraft.com/lists/item_id_list_pc.php

### give command

```
/give matlux1234 minecraft:acacia_boat 1
/give matlux1234 minecraft:acacia_sign 2
/give matlux1234 minecraft:acacia_sapling 2
/give matlux1234 minecraft:arrow 256
/give matlux1234 minecraft:shield 1
/give matlux1234 minecraft:netherite_pickaxe 1
/give matlux1234 minecraft:netherite_axe 2
/give matlux1234 minecraft:netherite_sword 2
/give matlux1234 minecraft:netherite_shovel 2
/give matlux1234 minecraft:netherite_boots 2
/give matlux1234 minecraft:netherite_chestplate 1
/give matlux1234 minecraft:netherite_helmet 1
/give matlux1234 minecraft:netherite_leggings 1
/give matlux1234 minecraft:netherite_boots 2
/give matlux1234 minecraft:diamond_axe 2
/give matlux1234 minecraft:diamond_sword 2
/give matlux1234 minecraft:diamond_shovel 2
/give matlux1234 minecraft:diamond_boots 2
/give matlux1234 minecraft:diamond_chestplate 1
/give matlux1234 minecraft:diamond_helmet 2
/give matlux1234 minecraft:diamond_leggings 2
/give matlux1234 minecraft:diamond_boots 2
/give matlux1234 minecraft:trapped_chest 5
/give matlux1234 minecraft:torch 256
/give matlux1234 minecraft:bow 2
/give matlux1234 minecraft:coal 64
/give matlux1234 minecraft:iron 64
/give matlux1234 minecraft:iron_ore 64
/give matlux1234 minecraft:apple 2
/give matlux1234 minecraft:gold_ore 2

/give matlux1234 minecraft:bone 10
/give matlux1234 minecraft:glass 512
/give matlux1234 minecraft:bucket 2
/give matlux1234 minecraft:blue_bed 2
/give matlux1234 minecraft:grass_block 2
/give matlux1234 minecraft:golden_carrot 2
/give matlux1234 minecraft:chest 2
/give matlux1234 minecraft:golden_apple 2
/give matlux1234 minecraft:cobblestone 2
/give matlux1234 minecraft:oak_plank 2


/tp lcn71 0 128 0
/tp lcn71 -300 64 -300
/tp lcn71 275 70 914

;; exile
/tp matlux1234 -300 64 -300


;;bed bridge near ice spike
/tp matlux1234 266 121 550

;; glass plan
/tp matlux1234 288 121 950

/tp lcn71 288 121 950


/tp matlux1234 0 81 290
/tp matlux1234 0 70 307
/tp matlux1234 0 121 307
/tp matlux1234 0 90 187
/tp matlux1234 0 121 187
/tp matlux1234 24 79 193
/tp matlux1234 -9 54 297
/tp matlux1234 0 121 40
/tp matlux1234 0 64 40
/tp matlux1234 -51 121 40
/tp matlux1234 -43 70 -8

;; underground bed
/tp matlux1234 -42 55 -71


;;corridor 
/tp matlux1234 -64 55 -71

/tp matlux1234 -64.5 121 -71.5
/tp matlux1234 -64 78 -71

;; connection south / west
/tp matlux1234 -10 55 -71

;; open ravin
/tp matlux1234 -120 55 -71



;; mineshaft
/tp matlux1234 -48 22 19


;; far away house
/tp matlux1234 -311 67 -71



/tp matlux1234 lcn71
/tp matlux1234 bubble_cream_

```