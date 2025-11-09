---

Spring Boot "Microservice" Example Project

This is a sample Java / Maven / Spring Boot (version 3.x) application that can be used as a starter for creating a microservice complete with built-in health check, metrics, Swagger 3 API docs, and much more.


---

🚀 How to Run

This application is packaged as a WAR/JAR with an embedded Tomcat 10 server.
No external Tomcat or JBoss installation is required.

Steps

1. Clone this repository


2. Use JDK 17+ and Maven 3.9+


3. Build the project and run tests:

mvn clean package


4. Run the application:

java -jar -Dspring.profiles.active=test target/spring-boot-rest-example-0.5.0.war

or

mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"


5. Check the terminal logs — if no exceptions are thrown, the app is running.



You should see something like:

Tomcat started on port(s): 8090 (http)
Started Application in 5.421 seconds


---

🏨 About the Service

The service is a simple Hotel Review REST API that uses Spring Data JPA with an in-memory H2 database by default.

You can also easily switch to MySQL or PostgreSQL using Spring profiles.

All REST endpoints are available on port 8090,
and operational endpoints (/actuator/*) on port 8091.


---

🌐 Features

✅ Built with Spring Boot 3.x, Spring Data JPA, and Jakarta EE 10
✅ Pagination support for hotel listings
✅ Exception handling with meaningful HTTP responses
✅ Swagger 3 / OpenAPI documentation (via springdoc-openapi)
✅ Actuator endpoints for health, metrics, and environment info
✅ Integrated MockMvc tests with @AutoConfigureMockMvc(addFilters = false) for simplicity
✅ Optional MySQL profile configuration


---

🧩 Endpoints Overview

🔍 System & Health Info

http://localhost:8091/actuator/health
http://localhost:8091/actuator/info
http://localhost:8091/actuator/metrics
http://localhost:8091/actuator/env


---

🏨 Hotel API

Create a Hotel

POST /example/v1/hotels
Accept: application/json
Content-Type: application/json

{
  "name" : "Beds R Us",
  "description" : "Very basic, small rooms but clean",
  "city" : "Santa Ana",
  "rating" : 2
}

RESPONSE: HTTP 201 (Created)
Location: http://localhost:8090/example/v1/hotels/1


---

Retrieve a paginated list of hotels

GET /example/v1/hotels?page=0&size=10

Response: HTTP 200
Body: Paginated hotel list (JSON)


---

Update a Hotel

PUT /example/v1/hotels/1
Accept: application/json
Content-Type: application/json

{
  "name" : "Beds R Us",
  "description" : "Very basic, small rooms but clean",
  "city" : "Santa Ana",
  "rating" : 3
}

RESPONSE: HTTP 204 (No Content)


---

🧾 API Documentation (Swagger 3 / OpenAPI)

Run the application and open:

http://localhost:8090/swagger-ui/index.html

Swagger UI automatically scans and documents all your controllers under com.khoubyari.example.api.rest.


---

🧰 Development Notes

All javax.* imports have been migrated to jakarta.*

All @RequestMapping methods now use specific HTTP annotations:
@GetMapping, @PostMapping, @PutMapping, and @DeleteMapping

Spring Boot 3 uses Jakarta Servlet 5+, so HttpServletResponse is now jakarta.servlet.http.HttpServletResponse

Security filters are disabled in tests via

@AutoConfigureMockMvc(addFilters = false)



---

🧪 Testing

Controller and service layers are tested using MockMvc and Mockito.

To run all tests:

mvn test


---

🗃️ Database Profiles

By default, the app runs on an in-memory H2 database.
To connect to MySQL, add the following in your application.yml:

---
spring:
  profiles: mysql
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/bootexample
    username: root
    password: password
  jpa:
    hibernate:
      ddl-auto: update
      dialect: org.hibernate.dialect.MySQLDialect

hotel.service:
  name: 'test profile:'

Run with:

java -jar -Dspring.profiles.active=mysql target/spring-boot-rest-example-0.5.0.war


---

💬 Author

Originally by Siamak Khoubyari
Updated for Spring Boot 3.x + Jakarta + Swagger 3 by Mithun