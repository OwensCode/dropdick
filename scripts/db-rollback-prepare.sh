#!/bin/sh

java -jar target/dropkick-*.jar db prepare-rollback hello-world.yml

