#!/bin/sh

java -jar target/dropkick-*.jar db drop-all --confirm-delete-everything hello-world.yml

