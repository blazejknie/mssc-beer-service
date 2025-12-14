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

## 1️⃣ Architecture & Flow Diagrams (Mermaid)

### 🌐 High-Level Microservices Architecture

```mermaid
flowchart LR
    Client --> Gateway
    Gateway --> Eureka

    Gateway --> BeerService
    Gateway --> OrderService

    OrderService --> InventoryService
    InventoryService --> FailoverService

    BeerService --> ConfigServer
    OrderService --> ConfigServer
    InventoryService --> ConfigServer

    ConfigServer --> ConfigSource[(Git Config Repo)]
```

---

### 🔍 Service Discovery Flow

```mermaid
sequenceDiagram
    participant Service
    participant Eureka

    Service->>Eureka: Register instance
    Eureka-->>Service: Registration confirmed

    Service->>Eureka: Heartbeat
    Eureka-->>Service: OK
```

---

### ⚙️ Configuration Loading Flow

```mermaid
sequenceDiagram
    participant Service
    participant ConfigServer
    participant GitRepo

    Service->>ConfigServer: Request configuration
    ConfigServer->>GitRepo: Fetch config files
    GitRepo-->>ConfigServer: application.yml
    ConfigServer-->>Service: Environment properties
```

---

## 2️⃣ Security & Gateway Flow

### 🔐 Security Responsibility Model

| Layer             | Responsibility                             |
| ----------------- | ------------------------------------------ |
| API Gateway       | Authentication, request filtering, routing |
| Internal Services | Authorization, service-to-service trust    |

---

### 🔁 Request Flow with Security

```mermaid
sequenceDiagram
    participant Client
    participant Gateway
    participant Eureka
    participant Service

    Client->>Gateway: HTTP Request
    Gateway->>Eureka: Resolve service name
    Eureka-->>Gateway: Service instance
    Gateway->>Service: Forward request
    Service-->>Gateway: Response
    Gateway-->>Client: Final response
```

### Notes

* Gateway is the **single external entry point**
* Services are **not exposed directly**
* Security policies can be centralized at the gateway level

---

## 3️⃣ How to Run the Whole System Locally

### ✅ Prerequisites

* Java 17+
* Maven 3.x
* Git

---

### ▶️ Startup Order (Important)

Start services in the following order:

1. **Config Source (Git repo)**

   * No runtime needed (configuration only)

2. **Config Server** (`mssc-brewery-config`)

   ```bash
   mvn spring-boot:run
   ```

3. **Eureka Server** (`mssc-brewery-eureka`)

   ```bash
   mvn spring-boot:run
   ```

4. **Core Services** (any order after Eureka)

   * `mssc-beer-service`
   * `mssc-beer-inventory-service`
   * `mssc-beer-order-service`
   * `mssc-beer-inventory-failover-service`

5. **API Gateway** (`mssc-brewery-gateway`)

---

### 🌍 Local Endpoints

| Component        | URL                                                      |
| ---------------- | -------------------------------------------------------- |
| Eureka Dashboard | [http://localhost:8761](http://localhost:8761)           |
| API Gateway      | [http://localhost:9090](http://localhost:9090) (example) |
| Config Server    | [http://localhost:8888](http://localhost:8888)           |

---

## 📌 Recommended README Placement

* **Each service README** → add:

  * Architecture Role
  * Mermaid diagram (subset)
* **Gateway README** → full security + routing flow
* **Root / docs repo** → this full document

---

## 🎯 Outcome

With these additions, the Brewery system documentation now:

* Explains **how services cooperate**
* Shows **real microservices flows visually**
* Is ready for **interviews, onboarding, and portfolio use**
