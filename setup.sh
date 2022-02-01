#!/bin/bash
set -o errexit
set -o nounset
set -o pipefail

function download_kafka() {
  wget -nc -O kafka_2.13-3.1.0.tgz https://dlcdn.apache.org/kafka/3.1.0/kafka_2.13-3.1.0.tgz
  tar xvf kafka_2.13-3.1.0.tgz
}

function download_zk() {
  wget -nc -O apache-zookeeper-3.7.0-bin.tar.gz https://dlcdn.apache.org/zookeeper/zookeeper-3.7.0/apache-zookeeper-3.7.0-bin.tar.gz
  tar xvf apache-zookeeper-3.7.0-bin.tar.gz
}

function start_zk() {
  cp ./apache-zookeeper-3.7.0-bin/conf/zoo_sample.cfg ./apache-zookeeper-3.7.0-bin/conf/zoo.cfg
  ./apache-zookeeper-3.7.0-bin/bin/zkServer.sh start
}

function start_kafka() {
  ./kafka_2.13-3.1.0/bin/kafka-server-start.sh ./kafka_2.13-3.1.0/config/server.properties
}

download_zk
download_kafka
start_zk
start_kafka
