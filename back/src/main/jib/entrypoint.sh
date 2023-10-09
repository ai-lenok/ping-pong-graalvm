#!/bin/sh

sleep "${START_DELAY_APP:-0}"
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -Djava.util.logging.manager=org.jboss.logmanager.LogManager -jar quarkus-run.jar  "$@"