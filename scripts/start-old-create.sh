#!/usr/bin/env bash
echo "starting old order create parser..."
source ~/.bashrc

env=$1
env=${env:-'dev'}
SPARK_HOME=${SPARK_HOME:-'/opt/spark2-hadoop2.6/'}
echo 'current path: 'pwd
echo "env: "$env
prop='conf/old_order_create_parser_'$env'.properties'
echo 'properties file: '$prop


${SPARK_HOME}/bin/spark-submit --conf spark.streaming.kafka.maxRatePerPartition=600 \
--class tasks.OrderParser --num-executors 1 --executor-cores 2 --executor-memory 3G --queue data \
pagesource-realtime-jobs-1.0.0-SNAPSHOT-jar-with-dependencies.jar $prop