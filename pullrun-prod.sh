#!/bin/bash
git pull
fuser -k 8886/tcp
rm -rf target/
mvn kotlin:compile
nohup mvn spring-boot:run -Dspring-boot.run.profiles=dev &

