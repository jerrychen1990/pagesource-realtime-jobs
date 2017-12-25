#!/usr/bin/env bash

echo "start rebuild"
env=$1
env=${env:-'dev'}
echo "env "$env
mvn clean -Dmaven.test.skip=true assembly:assembly -P $env
cp target/pagesource-realtime-jobs-1.0.0-SNAPSHOT-jar-with-dependencies.jar .
echo "rebuild finished"


