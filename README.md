[![CircleCI](https://dl.circleci.com/status-badge/img/gh/blazejknie/mssc-beer-service/tree/main.svg?style=svg&circle-token=c1c28063569eaa5c31fb5d6d71c6d30158f799be)](https://dl.circleci.com/status-badge/redirect/gh/blazejknie/mssc-beer-service/tree/main)
## 📦 mssc-beer-service

# 🍺 Beer Service

The **Beer Service** is a core business microservice responsible for managing the beer catalog within the Brewery microservices ecosystem.

## Responsibilities
- Manage Beer entities
- Expose REST APIs for beer data
- Register with Eureka Service Discovery
- Load configuration from Config Server

## Architecture Role
This service is consumed by:
- Beer Order Service
- API Gateway (external access)

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Cloud Netflix Eureka Client

## Running Locally
```bash
mvn clean spring-boot:run
