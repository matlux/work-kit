#!/bin/sh


if [ -z $1 ] ; then
ps faux | grep -v '\\(' | grep -v grep | grep member | sed 's#[^ ]*[ ]*\([^ ]*\).*member=\([^ ]*\).*#\1 \2#'
else
ps faux | grep -v "\\\(" | grep -v grep | grep member | sed 's#[^ ]*[ ]*\([^ ]*\).*member=\([^ ]*\).*#\1 \2#' | grep $1 | awk '{print $1}'

fi
