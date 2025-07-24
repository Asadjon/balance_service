# Balance Service

Handles user balance operations such as retrieving and updating balances. It communicates with the Auth Service for user validation and is part of the Transaction Management System microservices.

---

## 📜 Overview

The Balance Service is responsible for managing and updating the balance of each user. It is secured via JWT and only accessible through the API Gateway.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot 3.5.3
- Spring Validation
- PostgreSQL
- Redis
- Thymeleaf
- JWT (JSON Web Token)
- Docker

---

## 📦 Clone All Required Repositories
To run the full Transaction Management System, you'll need to clone each microservice repository into a common workspace folder. You can do it manually or with the following commands:

### 1. Create a project directory
   ```
   mkdir transaction-system && cd transaction-system
   ```

### > 2. Clone all required services
> * [auth_service](https://github.com/Asadjon/balance_service.git) 
> * [balance_service](https://github.com/Asadjon/balance_service.git) ← this repository
> * [transaction_service](https://github.com/Asadjon/transaction_service.git)
> * [api_gateway](https://github.com/Asadjon/api_gateway.git)

---

## 🚀 Running with Docker
### 1. Create app-network (only once)
If you haven't created the custom network yet, run:
```
docker network create app-network
```

### 2. Build and start the container
Inside the directory where your Dockerfile and docker-compose.yml are located (e.g., auth_service), run:
```
docker-compose up --build
```

### 3. Useful Docker commands
Inspect all containers connected to app-network:
```docker
docker network inspect app-network
```

Stop and remove the container(s):
```
docker-compose down
```

---

## 🔐 Authentication

All endpoints are protected using JWT. The token must be passed in the header:
```http
Authorization: Bearer <access_token>
```

---

## 🔁 API Endpoints

| Method | Endpoint                          | Description                         | Request Body            |
|--------|-----------------------------------|-------------------------------------|-------------------------|
| GET    | `/api/v1/balance/{userId}`        | Get the balance of a user           | Path variable: `userId` |
| POST   | `/api/v1/balance/change`          | Change (increase/decrease) balance  | `BalanceChangeRequest`  |


---

## 📦 Request & Response Body Structures

### 💰 Get Balance `GET /api/v1/balance/{userId}`

**Header:**
```html
Authorization: Bearer <access_token>
```
**Response:**
```json
{
    "user_id": 12,
    "current_balance": 2500.00,
    "last_updated": "2025-07-24T15:11:41.253616"
}
```

### 🔁 Change Balance `POST /api/v1/balance/change`
#### Response: ``` Email confirmed successfully. ```

### 🔄 Resend Verification Email `POST /api/v1/auth/resend-verification`

**Header:**
```html
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "userId": 1,
  "amount": 500.00,
  "type": "INCREASE"
}
```

**OR for decreasing balance:**
```json
{
  "userId": 1,
  "amount": 250.00,
  "type": "DECREASE"
}
```
**Response:** `Balance updated`