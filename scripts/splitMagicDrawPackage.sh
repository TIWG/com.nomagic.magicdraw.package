#!/bin/bash

set -e

if [[ $# -ne 1 ]]; then
  echo "# usage: $0 <file>"
  exit 1
fi

f=$1
size=$(stat --format="%s" $f)

mkdir -p parts

zipsplit -n 262144000 -b parts $f

stem=${f%.zip}

declare -i n=1
for p in parts/*.zip; do
  mv $p parts/$stem.part$n.zip
  n=$(($n+1))
done

ls -slh1 parts/*
