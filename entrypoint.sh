#!/bin/bash

TN_SIZE=${TN_SIZE:-150}

for file in /pics/*; do
  if [ -f "$file" ]; then

    ./thumbnail.sh "$file" -s "$TN_SIZE"
  fi
done