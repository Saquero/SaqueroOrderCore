<p align="center">
  <img src="assets/logo-saquero-ordercore.svg" width="130"/>
</p>

<h1 align="center">SaqueroOrderCore</h1>
<p align="center">
  Backend for order lifecycle management Ã¢â‚¬â€ Java 21 Ã‚Â· Spring Boot 3 Ã‚Â· Hexagonal Architecture Ã‚Â· DDD
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?style=flat-square&logo=springboot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql"/>
  <img src="https://img.shields.io/badge/Tests-19%20passing-success?style=flat-square"/>
  <img src="https://img.shields.io/badge/Architecture-Hexagonal-informational?style=flat-square"/>
</p>

---

## What is this?

SaqueroOrderCore is a production-style backend that manages the full lifecycle of orders:
creating orders, processing payments, cancelling orders and tracking payment history.

Built as a portfolio project to demonstrate real backend engineering practices:
clean architecture, domain-driven design, testable code and professional API design.

---

## Preview

### Swagger UI Ã¢â‚¬â€ All endpoints documented

![Swagger UI](assets/swagger-ui.png)

### pgAdmin Ã¢â‚¬â€ Database tables with real data

![pgAdmin](assets/pgadmin.png)

### Health Check Ã¢â‚¬â€ PostgreSQL status via Actuator

![Health Check](assets/health-check.png)

### Orders Summary Ã¢â‚¬â€ Real-time order count by status

![Orders Summary](assets/orders-summary.png)

---

## Tech Stack

| Technology     | Version | Role                 |
| -------------- | ------- | -------------------- |
| Java           | 21      | Language             |
| Spring Boot    | 3.4.5   | Framework            |
| PostgreSQL     | 16      | Database             |
| Flyway         | 10.x    | Schema migrations    |
| Docker Compose | v2      | Local infrastructure |
| JUnit 5        | 5.11.x  | Testing              |
| Mockito        | 5.x     | Mocking in tests     |
| Springdoc      | 2.8.x   | OpenAPI / Swagger UI |
| Maven          | wrapper | Build tool           |

---

## Architecture

Hexagonal Architecture + Clean Architecture + Tactical DDD.

```
Ã¢â€Å’Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€Â
Ã¢â€â€š          Infrastructure             Ã¢â€â€š
Ã¢â€â€š  (Controllers, JPA, Config)         Ã¢â€â€š
Ã¢â€â€š                                     Ã¢â€â€š
Ã¢â€â€š      Ã¢â€Å’Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€Â       Ã¢â€â€š
Ã¢â€â€š      Ã¢â€â€š     Application      Ã¢â€â€š       Ã¢â€â€š
Ã¢â€â€š      Ã¢â€â€š  (Use Cases, Ports)  Ã¢â€â€š       Ã¢â€â€š
Ã¢â€â€š      Ã¢â€â€š                      Ã¢â€â€š       Ã¢â€â€š
Ã¢â€â€š      Ã¢â€â€š   Ã¢â€Å’Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€Â   Ã¢â€â€š       Ã¢â€â€š
Ã¢â€â€š      Ã¢â€â€š   Ã¢â€â€š    Domain    Ã¢â€â€š   Ã¢â€â€š       Ã¢â€â€š
Ã¢â€â€š      Ã¢â€â€š   Ã¢â€â€š  (pure Java) Ã¢â€â€š   Ã¢â€â€š       Ã¢â€â€š
Ã¢â€â€š      Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€Ëœ   Ã¢â€â€š       Ã¢â€â€š
Ã¢â€â€š      Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€Ëœ       Ã¢â€â€š
Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€â‚¬Ã¢â€Ëœ
```

- **Domain** Ã¢â‚¬â€ pure Java, no framework dependencies, owns all business rules
- **Application** Ã¢â‚¬â€ use cases, ports (interfaces), commands, DTOs
- **Infrastructure** Ã¢â‚¬â€ JPA entities, Spring Data, REST controllers, mappers

See [ARCHITECTURE.md](ARCHITECTURE.md) for detailed design decisions.

---

## Order State Machine

```
CREATED Ã¢â€ â€™ PROCESSING Ã¢â€ â€™ PAID
CREATED Ã¢â€ â€™ PROCESSING Ã¢â€ â€™ FAILED
CREATED Ã¢â€ â€™ CANCELLED
```

State transitions are enforced by the domain model.
Any invalid transition throws `InvalidOrderStateTransitionException`.

---

## Getting Started (Windows)

### Requirements

- Java 21 (`java -version`)
- Docker Desktop running
- PowerShell

### 1. Clone the repo

```powershell
git clone https://github.com/Saquero/SaqueroOrderCore.git
cd SaqueroOrderCore
```

### 2. Start infrastructure

```powershell
docker compose up -d
```

### 3. Run the application

```powershell
# Standard mode
.\mvnw.cmd spring-boot:run

# Dev mode (loads seed data automatically)
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

App available at: `http://localhost:8080`

### 4. Open Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

### 5. Open pgAdmin (visual DB)

```
http://localhost:5050
```

Login: `admin@saquero.com` / `admin`

Connect to server:

- Host: `postgres`
- Port: `5432`
- Database: `ordercore`
- Username: `ordercore_user`
- Password: `ordercore_pass`

### Stop everything

```powershell
docker compose down
```

---

## API Endpoints

| Method | Path                   | Description                   |
| ------ | ---------------------- | ----------------------------- |
| POST   | /customers             | Create customer               |
| GET    | /customers/{id}        | Get customer by ID            |
| GET    | /customers/{id}/orders | Get all orders for a customer |
| POST   | /orders                | Create order                  |
| GET    | /orders                | List orders (with pagination) |
| GET    | /orders?status=CREATED | Filter orders by status       |
| GET    | /orders/summary        | Order count by status         |
| GET    | /orders/{id}           | Get order by ID               |
| POST   | /orders/{id}/pay       | Process payment               |
| POST   | /orders/{id}/cancel    | Cancel order                  |
| GET    | /orders/{id}/payments  | Payment history for order     |
| GET    | /actuator/health       | Health check with DB status   |

---

## Example Requests

### Create a customer

```powershell
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/customers" `
  -ContentType "application/json" `
  -Body '{"name": "John Doe", "email": "john@example.com"}' | ConvertTo-Json
```

### Create an order

```powershell
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/orders" `
  -ContentType "application/json" `
  -Body '{"customerId": "YOUR-CUSTOMER-UUID", "totalAmount": 99.99, "currency": "EUR"}' | ConvertTo-Json
```

### Process payment

```powershell
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/orders/YOUR-ORDER-UUID/pay" | ConvertTo-Json
```

### Cancel order

```powershell
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/orders/YOUR-ORDER-UUID/cancel" | ConvertTo-Json
```

### Order summary

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8080/orders/summary" | ConvertTo-Json
```

---

## Running Tests

```powershell
.\mvnw.cmd test
```

Current test suite: **19 tests, 0 failures**

- `OrderTest` Ã¢â‚¬â€ 7 tests, all state transitions including invalid ones
- `MoneyTest` Ã¢â‚¬â€ 8 tests, value object invariants and edge cases
- `ProcessPaymentUseCaseTest` Ã¢â‚¬â€ 3 tests with mocked ports

---

## Prepared for Kafka

The `infrastructure/adapter/out/events` package is ready for domain event publishing.

When a message broker is integrated, events like `OrderPaidEvent`, `OrderFailedEvent`
and `OrderCancelledEvent` will be published after state transitions Ã¢â‚¬â€ following
either direct publish or the Outbox pattern.

No domain changes required to add this.

---

## Project Structure

```
src/main/java/com/saquero/ordercore
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ domain
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ model          Order, Customer, Payment, OrderStatus, PaymentStatus
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ valueobject    Money
Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ exception      OrderDomainException, InvalidOrderStateTransitionException
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ application
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ port/in        Use case interfaces
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ port/out       Repository port interfaces
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ command        CreateOrderCommand, ProcessPaymentCommand...
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ dto            OrderResponse, PaymentResponse, PageResponse...
Ã¢â€â€š   Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ usecase        Use case implementations
Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ infrastructure
    Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ adapter/in/web      REST controllers, request DTOs
    Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ adapter/out/persistence  JPA entities, repositories, mappers
    Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ adapter/out/events  Prepared for Kafka
    Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ config              OpenAPI, CorrelationId filter, Dev initializer
    Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ exception           GlobalExceptionHandler, ApiError
```

---

## Part of the Saquero Backend Ecosystem

| Project                                                 | Stack            | Description                   |
| ------------------------------------------------------- | ---------------- | ----------------------------- |
| [SaqueroCloud](https://github.com/Saquero/SaqueroCloud) | .NET 8 + React   | SaaS admin platform, JWT auth |
| SaqueroOrderCore                                                | Java 21 + Spring Boot 3 | Order lifecycle backend, DDD, Hexagonal                |
| [SaqueroGateway](https://github.com/Saquero/SaqueroGateway)     | .NET 8                  | API Gateway -- single entry point                      |
