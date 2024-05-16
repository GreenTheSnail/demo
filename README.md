
# Monitoring rest application
Project to create, read, update and delete endpoints to monitore, monitore them on the background and get results (test for applifting).




## Tech Stack

**Operation system:** any 

**Backend:** Kotlin, Spring

**Application server:** Tomcat(Spring framework)

**Database:** MySql (Docker)

**Build:** Gradle

**Formating:** Ktlint

**Mock server:** Wiremock (Docker)

**Tetst framework:** Kotest

## Deployment

To deploy this project run

```bash
  docker compose up -d
```
it will create mySql database container and also create mock server with defined endpoint for test reasons (definition can be found in ../wiremock)

and then you can run DemoApplication (on startup it will create all needed tables and relations and also 3 test users) 


## Usage description
Base scenario:
1. Create monitored endpoint.
2. Wait.
3. Get all endpoints.
4. Get monitoring results by Id of endpoint you are intrested in.

**That is just a basic scenario, you can find some others request examaples in ../rest-api.**




## Authors

- [@vladislav_soshko](https://github.com/GreenTheSnail)

