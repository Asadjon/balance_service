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

## ⚙️ Setup Instruction
> You can view the installation manual in the [transaction-management-system](https://github.com/Asadjon/transaction-management-system/blob/master/README.md) repository.

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