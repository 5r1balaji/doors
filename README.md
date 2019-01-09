# Spring Boot Security Jwt Authentication using Spring Cloud
This project is the gateway for authenticating all requests to server

## Technology Used

 1. Spring Boot (2.1.1.RELEASE)
 2.  JWT (0.9.0)
 3.  Maria DB / h2
 4. Java 1.8

To consume api 
1. Signup
http://localhost:8081/doors-api/users/signup

{
"username":"sri",
"password":"password",
"email":"sreebalaji.vasanth@gmail.com",
"lastName":"balaji",
"firstName":"sri"
}

Response :

{
    "status": 200,
    "message": "User saved successfully.",
    "result": {
        "id": 2,
        "firstName": "sri",
        "lastName": "balaji",
        "username": "sri",
        "email": "sreebalaji.vasanth@gmail.com"
    }
}

2.To generate token :
http://localhost:8081/doors-api/token/generate-token

{
"username":"sri",
"password":"password"
}

for success :

{
    "status": 200,
    "message": "success",
    "result": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaXNzIjoiaHR0cDovL2Rvb3JzLmNvbSIsImlhdCI6MTU0NzAxNDkzOCwiZXhwIjoxNTQ3MDMyOTM4fQ.CtEnPIU8ksdL669HRXMrCUW6wcdZWvvPIrsD9PWHYf8",
        "username": "admin"
    }
}

for failure:
{
    "timestamp": "2019-01-09T06:22:42.151+0000",
    "status": 401,
    "error": "Unauthorized",
    "message": "Unauthorized",
    "path": "/doors-api/token/generate-token"
}
