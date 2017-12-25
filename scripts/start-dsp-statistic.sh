#!/usr/bin/env bash
source ~/.bashrc
env=$1
env=${env:-'dev'}

SPARK_HOME=${SPARK_HOME:-'/opt/spark2-hadoop2.6/'}
echo 'current path: 'pwd
echo "env: "$env
prop='./conf/order_create_dsp_'$env'.properties'
echo 'properties file: '$prop


${SPARK_HOME}/bin/spark-submit --conf spark.streaming.kafka.maxRatePerPartition=600 \
--class tasks.DspStatistic --num-executors 1 --executor-cores 2 --executor-memory 3G --queue data \
pagesource-realtime-jobs-1.0.0-SNAPSHOT-jar-with-dependencies.jar $prop