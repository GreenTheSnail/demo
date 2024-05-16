
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


## API Reference
### Create Monitored Endpoint (`/monitored-endpoints`)

#### Create monitored endpoint [POST]

- Summary: Create monitored endpoint
- Operation ID: createMonitoredEndpoint
- Tags: MonitoredEndpoint

##### Request Body

- Content Type: application/json
- Schema: MonitoredEndpointCreationEnvelope
- Required: Yes

##### Responses

- Response 201 (application/json)
    - Description: Monitored endpoint creation was successful
    - Schema: MonitoredEndpoint

- Response 400 (application/json)
    - Description: Bad Request.
    - Schema: BadRequest

### Delete Monitored Endpoint (`/monitored-endpoints/{endpointId}`)

#### Delete monitored endpoint [DELETE]

- Summary: Delete monitored endpoint
- Operation ID: deleteMonitoredEndpoint
- Tags: MonitoredEndpoint

##### Parameters

- endpointId (path, required, string): Monitored Endpoint ID.

##### Responses

- Response 200 (application/json)
    - Description: Monitored endpoint deletion was successful

- Response 404 (application/json)
    - Description: Resource not found.
    - Schema: NotFound
 
### Get Monitored Endpoint by ID (`/monitored-endpoints/{endpointId}`)

#### Get monitored endpoint by its ID [GET]

- Summary: Get monitored endpoint by its ID
- Operation ID: getMonitoredEndpointById
- Tags: MonitoredEndpoint

##### Parameters

- endpointId (path, required, string): ID of the monitored endpoint.

##### Responses

- Response 200 (application/json)
    - Description: Get of a monitored endpoint by ID was successful.
    - Schema: MonitoredEndpoint

- Response 404 (application/json)
    - Description: Resource not found.
    - Schema: NotFound

- Response 400 (application/json)
    - Description: Bad Request.
    - Schema: BadRequest

## Data Structures

### MonitoredEndpoint

- Type: object
- Description: Monitored Endpoint.
- Required:
  - id
  - name
  - url
  - dateOfLastUpdate
  - monitoredInterval
  - owner

#### Properties

- id: MonitoredEndpointId
- name: string
- url: string
- dateOfLastUpdate: string (ISO 8601 date)
- dateOfLastCheck: string (ISO 8601 date)
- monitoredInterval: integer
- owner: User

### MonitoredEndpointCreationEnvelope

- Type: object
- Description: Monitored endpoint creation object.
- Required:
  - name
  - url
  - monitoredInterval

#### Properties

- name: string
- url: string
- monitoredInterval: integer

### MonitoredEndpointId

- Type: string
- Format: uuid
- Description: Monitored Endpoint ID.
- Example: 456a8e62-f79d-4452-834c-dedbfddb395e

### User

- Type: object
- Description: User.
- Required:
  - id
  - username
  - email
  - accessToken

#### Properties

- id: UserId
- username: string
- email: string
- accessToken: UUID

### UserId

- Type: string
- Format: uuid
- Description: User ID.
- Example: 456a8e62-f79d-4452-834c-dedbfddb395e

## Responses

### NotFound

- Description: Resource not found.

### BadRequest

- Description: Bad Request.


## Authors

- [@vladislav_soshko](https://github.com/GreenTheSnail)

