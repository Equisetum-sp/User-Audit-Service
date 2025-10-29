# User Audit Service

#### A simple RESTful API for inputting user data asynchronously using Kafka.

## Tools utilized:
- **Java 17**
- **Spring boot 3.2.3**
- **Apache Kafka 4.1.0**
- **PostgreSQL 17.5**
- **Docker desktop and WSL 2**

## Basic flow:

HTTP request -> "Create User" service -> Kafka topic "user-events" -> "Receive user listener" consumer -> database 

## Available Endpoints:

- ### Create User
  Create user data asynchronously. The user data will be send to a kafka topic.

  **Method:** POST

  **Additional Headers:** none

  **Body:**
  - type: raw JSON
  - fields:
    - name (String, not null or blank, max. length 100)
    - email (String, not null or blank, max. length 100)
    - password (String, not null or blank, max. length 100)
  - Sample body:
    ```json
    {
        "name": "admin",
        "email": "tes@email.com",
        "password": "adminroot"
    }
    ```

  **Request Example:**
    ```shell
    curl --location --request POST 'http://localhost:8081/users' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "name": "admin",
        "email": "tes@email.com",
        "password": "adminroot"
    }'
    ```

  **Response Example**
    ```json
    {
        "errorCode": "0",
        "errorMessage": "Success"
    }
    ```

## How to run:
Clone this repository then run following maven command:
```shell
    mvn spring-boot:run
```
Alternatively, you can also use IDE such as IntelliJ to run this program

## Database and Kafka setup:
The SQL files to setup database and kafka are included in this project.

### Database
Scripts are located inside db folder. Make sure to have PostgreSQL installed in your device.
#### Files: 
- `create_db.sql` contains queries to create database. You may skip this if you prefer to use an existing database (in this case, make sure to adjust `spring.datasource.url` config in `application.yml`)
- `create_table.sql` contains queries to create user table

### Kafka
The `docker-compose.yml` file in kafka folder can be used to create container running Kafka server. The scripts are retrieved from Kafka Confluent website.

In Kafka folder, open a terminal and execute following command
``` shell
    docker compose up -d
```

This will execute the docker-compose file. The terminal will download latest Apache Kafka image and run it inside docker.

After successfully running the Kafka server, it can then be accessed from `localhost:9092`. Simply start the Spring Boot application and Spring-Kafka will automatically generate all topics required.  

To turn off the server, execute following command
``` shell
    docker compose down -v
```
