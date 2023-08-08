## UsersApp

To start, navigate to project dir and use:

> sudo docker-compose up --build


To find the IP, use:

> sudo docker network inspect  usersapp_spring-cloud-network | grep Gateway

### API

| # | Method | URI           | Success Status  | Optional json parameter  |
|---|--------|---------------|-----------------|--------------------------|
| 1 | GET    | /users/{uuid} | 200             |                          |
| 2 | POST   | /users        | 201             | "password"               |
| 3 | PUT    | /users/{uuid} | 200             | "password"               |
| 4 | DELETE | /users/{uuid} | 200             |                          |

User DTO json Request Body:

```
{
   "uuid" : "ee9d0c5b-a197-427b-b13a-c719f65ac1a1",
   "name" : "Name",
   "surname" : "Surname",
   "email" : "email@address.user",
   "password" : "passw0rd"
}
```

User DTO json Response Body:

```
{
   "uuid" : "ee9d0c5b-a197-427b-b13a-c719f65ac1a1",
   "name" : "Name",
   "surname" : "Surname",
   "email" : "email@address.user"
}
```

### Usage

To use the API use `data.json` file present in main dir

> curl -d "@data.json" -X POST http://172.17.0.1:8080/users -H "Content-Type: application/json" | json_pp

Then use the UUID that was generated

To GET:

> curl http://172.17.0.1:8080/users/dcaea679-8a2a-4857-bf9a-3839b685e9c0  | json_pp

To PUT:

> curl -X PUT -d "@data.json" -H "Content-Type: application/json" http://172.17.0.1:8080/users/dcaea679-8a2a-4857-bf9a-3839b685e9c0  | json_pp

To DELETE:

> curl -X DELETE http://172.17.0.1:8080/users/dcaea679-8a2a-4857-bf9a-3839b685e9c0  | json_pp

```
.
├── data.json
├── docker-compose.yml
├── README.md
├── usersapp
│   ├── dockerfile
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── usersapp
│   │   │   │           ├── base
│   │   │   │           │   └── BaseEntity.java
│   │   │   │           ├── error
│   │   │   │           │   ├── ErrorDTO.java
│   │   │   │           │   ├── ErrorService.java
│   │   │   │           │   ├── exception
│   │   │   │           │   │   └── ResourceNotFoundException.java
│   │   │   │           │   └── GlobalExceptionHandler.java
│   │   │   │           ├── user
│   │   │   │           │   ├── UserController.java
│   │   │   │           │   ├── UserDTO.java
│   │   │   │           │   ├── User.java
│   │   │   │           │   ├── UserRepository.java
│   │   │   │           │   ├── UserServiceImpl.java
│   │   │   │           │   └── UserService.java
│   │   │   │           └── UsersAppApplication.java
│   │   │   └── resources
│   │   │       └── application.properties
│   │   └── test
│   │       ├── java
│   │       │   └── com
│   │       │       └── usersapp
│   │       │           └── user
│   │       │               ├── UserControllerTest.java
│   │       │               └── UserServiceImplTest.java
│   │       └── resources
│   │           └── application-test.properties
└── usersdb
    ├── db.sql
    └── dockerfile
```
