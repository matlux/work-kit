#!/bin/sh

for i in {50..69}; do 
  host=server50$i
  echo on $host execute: $*
  ssh -o StrictHostKeyChecking=no -p 222 $host $*
done
