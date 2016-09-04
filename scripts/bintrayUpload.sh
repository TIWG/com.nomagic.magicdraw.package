#!/usr/bin/env bash

#
# This is an alternative upload method using the JFrog CLI
# See: https://www.jfrog.com/getcli/
#

bintray_path="tiwg/org.omg.tiwg.vendor.nomagic/com.nomagic.magicdraw.package"

ls -slh1 ${f}.part*

read -e -p "Version to upload: " version

read -e -n 1 -p "OK to upload to $bintray_path, version=$version (y/n) ? " ok
if [[ "$ok" != "y" ]]; then
  echo "# Cancelled"
  exit -1
fi

for p in ${f}.part*; do
  echo "# Uploading $p..."
  jfrog bt u $p $bintray_path/$version
done


