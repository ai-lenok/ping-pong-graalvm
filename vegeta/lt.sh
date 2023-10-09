#!/usr/bin/env bash

HOST="GRAALVM_HOST"

METHOD="api/v1/ping"
URL="http://$HOST/$METHOD"

DURATION="600s"
WORKERS=1000
RATE=100
RESULT_FILE="results.bin"

DATE=$(date)

echo "RATE: ${RATE}   duration: ${DURATION}   start time: ${DATE}"

echo "GET ${URL}" | \
vegeta attack -duration=$DURATION -max-workers=$WORKERS -rate=$RATE | \
tee $RESULT_FILE | \
vegeta report | head -n 7

./histogram.sh