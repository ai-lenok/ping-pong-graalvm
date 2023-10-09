#!/usr/bin/env bash

RESULT_FILE="results.bin"

cat $RESULT_FILE | \
vegeta report -type="hist[0,100ms,500ms,1000ms,1500ms]"