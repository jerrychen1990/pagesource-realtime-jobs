#!/usr/bin/env bash
if [ $# != 1 ] ;
then
echo 'Usage: deploy $target machine name'
else
target=$1
jar_name='pagesource-realtime-jobs-1.0.0-SNAPSHOT-jar-with-dependencies.jar'


echo 'deploying jar to '${target}
echo 'copying jar...'
scp  ${jar_name} xiaowa@${target}:~/
echo 'moving jar to app dir'
ssh ${target} sudo cp ${jar_name} /data/users/app/pagesource-realtime-jobs
echo 'deploying jar finished'
fi