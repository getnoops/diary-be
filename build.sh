#!/bin/bash

./gradlew clean fe build
docker build -t contextcloud/diary-be:latest .
