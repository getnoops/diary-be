#!/bin/bash

./gradlew clean build
docker build -t contextcloud/diary-be:latest .
