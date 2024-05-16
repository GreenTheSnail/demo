
# Monitoring rest application
Project to create, read, update and delete endpoints to monitor, monitor them in the background and get results (test for applifting).



## Tech Stack

**Operation system:** any 

**Backend:** Kotlin, Spring

**Application server:** Tomcat(Spring framework)

**Database:** MySql (Docker)

**Build:** Gradle

**Formating:** Ktlint

**Mock server:** Wiremock (Docker)

**Test framework:** Kotest

## Deployment

To deploy this project run

```bash
  docker compose up -d
```
It will create mySql database container and also create mock server with a defined endpoint for test reasons (definition can be found in ../wiremock)

Then you can run DemoApplication (on startup it will create all needed tables and relations and also 3 test users) 


## Usage description
Base scenario:
1. Create monitored endpoint.
2. Wait.
3. Get all monitored endpoints.
4. Get monitoring results by Id of an endpoint you are interested in.

**That is just a basic scenario, you can find some other request examples in ../rest-api.**
(I used them for my tests, you also can use them, if you want)

## Junit tests
Also, i have created several junit tests, not for all features, but for the most important.

## P.S.
I had some fun doing this task, it was really interesting and I learned some new things.
Thank you in advance, for your time and code review.

## Authors

- [@vladislav_soshko](https://github.com/GreenTheSnail)
