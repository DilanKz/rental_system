# Vehicle Rental Service

## Description

Users can request vehicles for travel purposes within the application.

## Installation

To install and run this project, follow these steps:

1. Ensure you have Java JDK 17 installed. If not, download and install it from [Java SE Downloads](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).

2. Clone this repository to your local machine using `https://github.com/DilanKz/rental_system.git`.

3. Navigate to the project directory.

4. Run the project:

5. Access the application in your web browser at `http://localhost:8080`.


## Documentation

### Postman Collection

To explore and test the endpoints of this API, you can use the Postman collection provided in this repository.

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/28181150/2sA2rGwKua)


## Endpoints

### Authentication

##### Login
- Method: POST
- Path: http://localhost:8080/auth/login
- Description: Endpoint for user login. Returns a JWT token upon successful authentication.

#### Register
- Method: POST
- Path: http://localhost:8080/auth/register
- Description: Endpoint for user registration. Returns a success message upon successful registration.

### User

#### Update Account
- Method: PUT
- Path: http://localhost:8080/user
- Description: Endpoint for updating user account information. Returns a success message upon successful update.

### Vehicle

#### Save
- Method: POST
- Path: http://localhost:8080/vehicle
- Description: Endpoint for saving a new vehicle. Returns a success message upon successful save.

#### Update
- Method: PUT
- Path: http://localhost:8080/vehicle
- Description: Endpoint for updating an existing vehicle. Returns a success message upon successful update.

#### Find All
- Method: GET
- Path: http://localhost:8080/vehicle
- Description: Endpoint for retrieving all vehicles.

#### Get One
- Method: GET
- Path: http://localhost:8080/vehicle/{id}
- Description: Endpoint for retrieving a specific vehicle by its ID.

#### Find All By Plate Number
- Method: GET
- Path: http://localhost:8080/vehicle/platenumber
- Description: Endpoint for retrieving all vehicles that match the specified plate number.

#### Find All By Model
- Method: GET
- Path: http://localhost:8080/vehicle/model
- Description: Endpoint for retrieving all vehicles that match the specified models.

#### Find All By Date
- Method: GET
- Path: http://localhost:8080/vehicle/date
- Description: Retrieves a list of vehicles that are available on or before the specified date.

### Request

#### Get All Requests
- Method: GET
- Path: http://localhost:8080/request
- Description: Retrieves all ride requests.

#### Get One Request
- Method: GET
- Path: http://localhost:8080/request/{id}
- Description: Retrieves a single ride request by its ID.

#### Get Request On Status
- Method: GET
- Path: http://localhost:8080/request/status/{status}
- Description: Retrieves ride requests based on their status.

#### Get Request By Location
- Method: GET
- Path: http://localhost:8080/request/locations
- Description: Retrieves ride requests based on the specified pickup and destination locations.

#### Get Request By Date
- Method: GET
- Path: http://localhost:8080/request/date
- Description: Retrieves ride requests filtered by the specified pickup date.

#### Get Request By Dates
- Method: GET
- Path: http://localhost:8080/request/dates
- Description: Retrieves ride requests filtered by the specified pickup date range.

#### Add Request
- Method: POST
- Path: http://localhost:8080/request
- Description: Adds a new ride request.

#### Update Request
- Method: PUT
- Path: http://localhost:8080/request
- Description: Updates an existing ride request.

#### Update Request Status
- Method: PATCH
- Path: http://localhost:8080/request/{id}
- Description: Updates the status of an existing ride request.

#### Assign Vehicle
- Method: PUT
- Path: http://localhost:8080/request/{id}
- Description: Endpoint for assigning a vehicle to an existing ride request.

#### Get All Requests By User ID
- Method: GET
- Path: http://localhost:8080/request/all/{id}
- Description: Retrieves all ride requests by user ID with proper authorization.

**Note:** The endpoints listed above work assuming the application is running on port 8080.

## Project Information

### Java Version
Java version: 17

### Spring Boot Version
Spring Boot version: 3.2.3

### Libraries Used
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-web
- spring-boot-devtools
- mysql-connector-j
- lombok
- jjwt-api
- jjwt-impl
- jjwt-jackson