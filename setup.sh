#!/bin/bash
set -o errexit
set -o nounset
set -o pipefail

wget -nc -O kafka_2.13-3.1.0.tgz https://dlcdn.apache.org/kafka/3.1.0/kafka_2.13-3.1.0.tgz
tar xvf kafka_2.13-3.1.0.tgz
