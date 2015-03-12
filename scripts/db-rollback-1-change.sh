#!/bin/sh

java -jar target/dropkick-*.jar db rollback -c 1 hello-world.yml

