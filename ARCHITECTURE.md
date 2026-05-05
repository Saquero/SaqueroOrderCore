# Architecture Decision Records — SaqueroOrderCore

## Overview

SaqueroOrderCore is a backend system for managing the full lifecycle of orders,
built with Hexagonal Architecture, Clean Architecture principles and tactical DDD.

The goal is to keep the domain pure and independent of any framework,
making the business logic testable, maintainable and ready to scale.

---

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                      Infrastructure                         │
│                                                             │
│   REST Controllers        JPA Adapters       Event Stubs    │
│   (OrderController)   (OrderPersistence)   (events/)        │
│                                                             │
│         │                     │                            │
│         ▼                     ▼                            │
│  ┌─────────────────────────────────────┐                   │
│  │           Application               │                   │
│  │                                     │                   │
│  │  Use Cases         Ports            │                   │
│  │  (CreateOrder)     (in / out)       │                   │
│  │  (ProcessPayment)  (interfaces)     │                   │
│  │  (CancelOrder)                      │                   │
│  │                                     │                   │
│  │       ┌─────────────────────┐       │                   │
│  │       │       Domain        │       │                   │
│  │       │                     │       │                   │
│  │       │  Order  Customer    │       │                   │
│  │       │  Payment  Money     │       │                   │
│  │       │  OrderStatus        │       │                   │
│  │       │  (pure Java)        │       │                   │
│  │       └─────────────────────┘       │                   │
│  └─────────────────────────────────────┘                   │
└─────────────────────────────────────────────────────────────┘
```

Dependencies always point inward. Domain knows nothing about Spring, JPA or HTTP.

---

## Layer Responsibilities

### Domain

- Pure Java, zero framework dependencies
- Contains: `Order`, `Customer`, `Payment`, `Money`, `OrderStatus`, `PaymentStatus`
- Business rules live here: state transitions, validations, invariants
- `Money` is a value object with currency and immutability guarantees
- `Order` enforces its own state machine — no external code can bypass it

### Application

- Orchestrates domain objects to fulfill use cases
- Defines ports (interfaces) that infrastructure must implement
- Contains: use cases, commands, DTOs, port interfaces
- No business logic here — only coordination
- Ports in: `CreateOrderUseCase`, `ProcessPaymentUseCase`, `CancelOrderUseCase`
- Ports out: `OrderRepositoryPort`, `CustomerRepositoryPort`, `PaymentRepositoryPort`

### Infrastructure

- Implements ports defined in application layer
- Contains: JPA entities, Spring Data repositories, mappers, REST controllers
- JPA entities are never exposed outside this layer
- Mappers translate between JPA entities and domain models
- Controllers are thin: validate input, call use case, return response

---

## Key Design Decisions

### 1. No repositories in domain

Repositories are contracts (interfaces) defined in `application/port/out`.
Implementations live in `infrastructure/adapter/out/persistence`.
This keeps the domain free of persistence concerns.

### 2. No Lombok

Lombok hides code that belongs to the domain model.
Explicit constructors and getters make the code readable and debuggable
without IDE plugins or annotation processors.

### 3. ddl-auto: validate

Flyway owns the schema. Hibernate only validates against it.
This prevents accidental schema changes in any environment.

### 4. Money as value object

Amounts are never stored as plain `double` or `float`.
`Money` wraps `BigDecimal` with scale 4 and currency validation,
preventing floating point errors in financial calculations.

### 5. Order state machine in the domain

`Order` enforces its own transitions:

```
CREATED → PROCESSING → PAID
CREATED → PROCESSING → FAILED
CREATED → CANCELLED
```

Any invalid transition throws `InvalidOrderStateTransitionException`.
This rule cannot be bypassed from outside the domain.

### 6. Simulated payment processor

`ProcessPaymentUseCaseImpl` simulates an 80% success rate.
The `infrastructure/adapter/out/events` package is prepared for a real
payment gateway or Kafka event publication in the future.

---

## Prepared for Kafka

The package `infrastructure/adapter/out/events` is intentionally empty.
When a message broker is introduced, domain events such as:

- `OrderPaidEvent`
- `OrderFailedEvent`
- `OrderCancelledEvent`

will be published from use cases after successful state transitions,
following either a direct publish or Outbox pattern.

Use cases already return enough state to build these events without
changing the domain layer.

---

## Package Structure

```
src/main/java/com/saquero/ordercore
├── SaqueroOrderCoreApplication.java
├── domain
│   ├── model          (Order, Customer, Payment, OrderStatus, PaymentStatus)
│   ├── valueobject    (Money)
│   ├── exception      (OrderDomainException, InvalidOrderStateTransitionException)
│   └── service        (reserved for future domain services)
├── application
│   ├── port
│   │   ├── in         (use case interfaces)
│   │   └── out        (repository port interfaces)
│   ├── command        (CreateOrderCommand, ProcessPaymentCommand, CancelOrderCommand)
│   ├── dto            (OrderResponse, PaymentResponse, CustomerResponse, PageResponse)
│   └── usecase        (use case implementations)
└── infrastructure
    ├── adapter
    │   ├── in
    │   │   └── web    (OrderController, CustomerController, request DTOs)
    │   └── out
    │       ├── persistence  (JPA entities, repositories, mappers, adapters)
    │       └── events       (prepared for Kafka/event publishing)
    ├── config         (OpenApiConfig, CorrelationIdFilter, DevDataInitializer)
    └── exception      (GlobalExceptionHandler, ApiError)
```

---

## Testing Strategy

- **Domain tests**: pure unit tests, no Spring context, no mocks needed
- **Use case tests**: Mockito mocks for ports, test business logic in isolation
- **No integration tests yet**: prepared for `@SpringBootTest` with Testcontainers

Current coverage:

- `OrderTest` — 7 tests covering all state transitions
- `MoneyTest` — 8 tests covering value object invariants
- `ProcessPaymentUseCaseTest` — 3 tests with mocked ports

---

## API Endpoints

| Method | Path                   | Description                   |
| ------ | ---------------------- | ----------------------------- |
| POST   | /customers             | Create customer               |
| GET    | /customers/{id}        | Get customer by ID            |
| GET    | /customers/{id}/orders | Get all orders for a customer |
| POST   | /orders                | Create order                  |
| GET    | /orders                | List orders with pagination   |
| GET    | /orders/summary        | Order count by status         |
| GET    | /orders/{id}           | Get order by ID               |
| POST   | /orders/{id}/pay       | Process payment               |
| POST   | /orders/{id}/cancel    | Cancel order                  |
| GET    | /orders/{id}/payments  | Payment history for order     |
| GET    | /actuator/health       | Health check                  |
