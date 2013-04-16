#!/bin/sh

while x=0; do clear; ps faux | grep -E '(myuser|slot|member|slot|target|repl|cpu|gpu|address)' --color=always | grep -vE '(sshd|grep|bash|less)'; sleep 2; done
