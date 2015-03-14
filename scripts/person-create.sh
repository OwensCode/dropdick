#!/bin/sh -x

name=$1
title=$2

curl -H 'Content-Type: application/json' -H 'Accept: application/json' \
    -X POST -d "{ \"fullName\": \"${name}\", \"jobTitle\": \"${title}\" }" \
    'http://localhost:8080/people'

echo $?
