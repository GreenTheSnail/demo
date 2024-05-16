
# Monitoring rest application
Project to crud endpoints to monitor, monitor on the background and get results (test for applifting).




## Tech Stack

**Operation system:** any 

**Backend:** Kotlin, Spring

**Application server:** Tomcat(Spring framework)

**Database:** MySql (Docker)

**Build:** Gradle

**Formating:** Ktlint

**Mock server:** Wiremock

**Tetst framework:** Kotest

## Deployment

To deploy this project run

```bash
  docker compose up -d
```
it will create mySql database container and also create mock server with defined endpoint for test reasons(definition can be found in ../wiremock)

and then you can run DemoApplication(on startup it will create all needed tables and relations and also 3 test users) 


## API Reference

### Create User (`/user`)

#### Create user [POST]

- **Summary**: Create user
- **Operation ID**: createUser
- **Tags**: User

##### Request Body

- **Content Type**: application/json
- **Schema**: [UserCreationEnvelope](#usercreationenvelope)
- **Required**: Yes

##### Responses

- **Response 201 (application/json)**

    - **Description**: User creation was successful
    - **Schema**: string

- **Response 400 (application/json)**

    - **Description**: Bad Request.
    - **Schema**: [BadRequest](#badrequest)

### Delete User (`/user/{userId}`)

#### Delete user [DELETE]

- **Summary**: Delete user
- **Operation ID**: deleteUserById
- **Tags**: User

##### Parameters

- `userId` (path, required, string): User ID.

##### Responses

- **Response 200 (application/json)**

    - **Description**: User deletion was successful

- **Response 404 (application/json)**

    - **Description**: Resource not found.
    - **Schema**: [NotFound](#notfound)
 
### Get User by Identification Number (`/user/{identificationNumber}`)

#### Get user by its identification number [GET]

- **Summary**: Get user by its identification number
- **Operation ID**: getUsersByIdentificationNumber
- **Tags**: User

##### Parameters

- `identificationNumber` (path, required, string): Identification number of the user.

##### Responses

- **Response 200 (application/json)**

    - **Description**: Get of an user by identification number was successful.
    - **Schema**: [User](#user)

- **Response 404 (application/json)**

    - **Description**: Resource not found.
    - **Schema**: [NotFound](#notfound)

- **Response 400 (application/json)**

    - **Description**: Bad Request.
    - **Schema**: [BadRequest](#badrequest)

## Data Structures

### User

- **Type**: object
- **Description**: User.
- **Required**:
  - id
  - name
  - surname
  - identificationNumber
  - age

#### Properties

- `id`: [UserId](#userid)
- `name`: [Name](#name)
- `surname`: [Surname](#surname)
- `identificationNumber`: [IdentificationNumber](#identificationnumber)
- `age`: [Age](#age)

### UserCreationEnvelope

- **Type**: object
- **Description**: User creation object.
- **Required**:
  - name
  - surname
  - identificationNumber

#### Properties

- `name`: [Name](#name)
- `surname`: [Surname](#surname)
- `identificationNumber`: [IdentificationNumber](#identificationnumber)

### UserId

- **Type**: string
- **Format**: uuid
- **Description**: User ID.
- **Example**: 456a8e62-f79d-4452-834c-dedbfddb395e

### IdentificationNumber

- **Type**: string
- **Description**: Identification number of an user.
- **Example**: 100510/6219

### Name

- **Type**: string
- **Description**: Name of an user.
- **Example**: Paul

### Surname

- **Type**: string
- **Description**: Surname of an user.
- **Example**: White

### Age

- **Type**: integer
- **Description**: Age of an user.
- **Example**: 18

## Responses

### NotFound

- **Description**: Resource not found.

### BadRequest

- **Description**: Bad Request.



## Authors

- [@vladislav_soshko](https://github.com/GreenTheSnail)

