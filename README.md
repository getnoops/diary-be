# What this mess is
Context Cloud needs a sample "customer's" application to demonstrate its deployment into the platform.

This repo is the back-end of such Demo app, which is an extremely simple web application, implementing an online diary.
There is no authentication to this app, the only screen allows to list the last few entries and create a new one.
The app uses Postgres DB for persistence.

# Running the app locally
The app is wrapped into a Docker image and pushed to docker hub to be able to run it directly without building.  
There is a `docker-compose.yml` file in the root directory, which can be used to start both the back-end app and the database.
```shell
docker compose up
```

alternatively use the `run.sh` and `stop.sh` scripts.


# Calling the APIs

There's two endpoints to call

## Get Items

GET http://localhost:8080/api/entries

```shell
curl http://localhost:8080/api/entries
```

Sample response:

```json
[
    {
        "dateTime": "07/02/2023 12:51",
        "text": "first one!!!"
    },
    {
        "dateTime": "07/02/2023 12:41",
        "text": "second one!!!"
    }
]
```

## Create an Entry

POST http://localhost:8080/api/entry

```shell
curl -X POST http://localhost:8080/api/entry \
  -H "Content-Type: application/json" \
   -d '{"text": "first one!!!"}'
```

Response is an empty HTTP 200 (for now).  
The text will be truncated to first 250 symbols.

# Building
## Pre-requisites
* Java 17
* Gradle
* Be a contributor for `contextcloud` organization on GitHub.

## Build only

The `build.sh` script will build the app and package it into Docker (locally) with tag `contextcloud/diary-be:latest`

```shell
./build.sh
```

## Build and Push

To build and push to Context Cloud repo on GitHub, run

```shell
build-and-push.sh
```