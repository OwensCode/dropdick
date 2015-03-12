#!/bin/sh

java -jar target/dropkick-*.jar db migrate --dry-run hello-world.yml

