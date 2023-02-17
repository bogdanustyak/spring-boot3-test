## Getting started

Spring boot test assignment. 

### Assignment details

Generate a Kotlin / Spring Boot app for a REST api that describes the following entity: Person(name, surname, email,
phone, dateOfBirth, age, username, password).

The app should support:

* <del>In-memory user-based auth(create 2 test users/roles "admin" and "guest").</del>
* <del>JPA/Hibernate with in-memory H2 </del>
* <del>Standard REST CRUD for the entity (with pagination, only admin can access).</del>
* <del>Extra endpoint that returns a list of users that can be filtered by name (also partial) and/or age (admin and
  guest
  can access). Username and password should not be returned on this endpoint.</del>
* <del> Customised API error response.</del>

Bonus: choose 1 of the following tasks:

1. Integrate a simple hibernate search on the entity and expose it with a search endpoint
2. <del> Create an optimised docker image </del>
3. Expose the standard actuators and create a custom one showing the number of persons in the table.

### How to run with Docker

#### First build the jar file

```bash
./gradlew bootJar
```

#### Build and run docker image

```bash
docker build . -t test-api:latest
```

```bash
docker run -p 8080:8080 -t test-api:latest
```

### App credentials

#### Admin

````
* username: admin
* password: admin
````

#### Guest

````
* username: guest
* password: guest
````

### Nice to have

* Swagger for API documentation
* Extends tests
* Use cases definitions and implementation 
* Extend error handling
* Add more validations
