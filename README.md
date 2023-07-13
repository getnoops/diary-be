# What this is
Context Cloud needs a sample "customer's" application to demonstrate its deployment into the platform.

This repo is the back-end of such Demo app, which is an extremely simple web application, implementing an online diary.
There is no authentication to this app, the only screen allows to list the last few entries and create a new one.
The app uses Postgres DB for persistence.

# Building the App
## Pre-requisites
* Java 17
* Gradle
* Be a contributor for `contextcloud` organization on GitHub.

The app is wrapped into a Docker container, which includes both front-end and the back-end.    
For this to work, you need to have the diary-app repo checked out to the same parent directory as this repo.
Once both **diary-app** and **diary-be** are next to each other, the `build.sh` script will first build the FE app in its own directory
and copy the resulting static files over here into the **resources** folder, allowing both FE and BE to run as a single application.  
Then the script will package both FE and BE into a SpringBoot application and then build a docker container for it.

Use `build-and-push.sh` to build everything and push the docker image to DockerHub so you can run everything locally with compose.

# Running the app locally (build and run containers)
The `docker-compose.yml` starts both the App and its database.
The all-in-one script that builds everything and runs it is
```shell
build-and-run.sh
```

# Running the app locally from source
```shell
build.sh
./gradlew bootRun
```

# Accessing the UI
The address is
```shell
http://localhost:8080/
```

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
    "ts": 1675846708178,
    "dateTime": "08/02/2023 08:58",
    "text": "two"
  },
  {
    "ts": 1675846702721,
    "dateTime": "08/02/2023 08:58",
    "text": "one"
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


# Environment Variables for Deployment
When Context Cloud deploys the app, it needs to hook it up to the database, which will be provisioned by ContextCloud rather than the one defined in the `docker-compose.yml`  
To define RDS DB connection parameters this application watches the following three environment variables:

| Env. Var | Default value (fallback) |
|----------|--------------------------|
|RDS_DB_URL|jdbc:postgresql://diary-db:5432/diary|
|RDS_DB_USERNAME|diary_admin|
|RDS_DB_PASSWORD|123456789|

See `docker-compose.yml` for details about fallback values and how they are used.  
Postgres version used is 14.