# 📋 Job Tracker Backend (Spring Boot + JWT + PostgreSQL)

A secure REST API for a Job Tracker application built using Spring Boot, JWT Authentication, and PostgreSQL.

---

## 🚀 Features

-✅ User Registration

-✅ User Login (JWT Authentication)

-✅ Create Job Applications

-✅ Update Job Application Status

-✅ Delete Job Applications

-✅ View All Job Applications

-✅ Secure Endpoints with JWT

-✅ PostgreSQL Database Integration

---

## 🛠 Tech Stack

Backend: Spring Boot
Security: Spring Security + JWT
Database: PostgreSQL
ORM: Hibernate / JPA
Build Tool: Maven
API Testing: Postman

---

## 📂 Project Structure

```
com.example.jobtracker
│
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── repository
├── security
├── service
└── service.impl
```

---

## 🔐 Authentication Flow

1. User registers
2. User logs in
3. JWT token is generated
4. Token must be sent in header:

```
Authorization: Bearer <your-token>
```

All `/api/jobs/**` endpoints require authentication.

---

## 📦 API Endpoints

###👤 User APIs

| Method | Endpoint | Description |
|--------|----------|------------|
| POST | `/api/users/register` |	Register new user|
| POST | `/api/users/login`	| Login and receive JWT|

###📊 Job Tracker APIs (Protected)

| Method | Endpoint | Description |
|--------|----------|------------|
| POST |	`/api/jobs`	| Create new job application |
| GET	| `/api/jobs`	| Get all job applications |
|GET	| `/api/jobs/{id}`	| Get job by ID |
|PUT | `/api/jobs/{id}`	| Update job application |
|DELETE	| `/api/jobs/{id}`	| Delete job application |

---

## ⚙️ Setup Instructions

###1️⃣ Clone Repository

```bash
git clone https://github.com/sharief44/jobtracker-backend.git
cd jobtracker-backend
```
---

###2️⃣ Configure PostgreSQL

Create database:

```
jobtrackerdb
```

Update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jobtrackerdb
    username: postgres
    password: yourpassword

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```
---

##3️⃣ Run Application

```bash
mvn spring-boot:run
```

Application runs on:

```
http://localhost:8080
```

##🧪 Testing with Postman

1.Register a user

2.Login to receive JWT

3. Add JWT in headers:

```
Authorization: Bearer <token>
```

4.Access job APIs

---

##📈 Future Improvements

-Email Verification

-Swagger API Documentation

-Docker Containerization

-Unit & Integration Testing

-Cloud Deployment

-CI/CD Pipeline

---
##👨‍💻 Author

Sharief Sk
Full Stack Java Developer

---

##⭐ Support

If you like this project, give it a ⭐ on GitHub!
