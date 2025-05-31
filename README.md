# 🔐 Spring Security Auth Modules

This repository contains modular implementations of **Spring Security authentication mechanisms**, designed to be clean, reusable, and extensible. The focus is on demonstrating multiple approaches to **Basic Authentication** and **JWT Authentication** using different user sources.

---

## 📦 Modules

### 1. `basic_auth`
- **Description**: Basic Auth using a single user defined in `application.properties`.
- **Use Case**: Quick-start or demo apps where credentials are hardcoded via config.

### 2. `basic_auth_in_memory_user`
- **Description**: Basic Auth with multiple users defined using an in-memory `UserDetailsService`.
- **Use Case**: Simple role-based access control for prototypes or internal tools.

### 3. `basic_auth_db_user`
- **Description**: Basic Auth with users and roles stored in a database.
- **Use Case**: Production-ready authentication with persistent user storage.

### 4. `jwt_auth_in_memory_user`
- **Description**: JWT-based authentication using in-memory user details.
- **Use Case**: Stateless APIs with token-based auth for testing or dev purposes.

---

## 🔧 Features

- 🔒 **Spring Security Basic Auth** (Single user, In-memory, Database)
- 🔑 **JWT Authentication** (In-memory users for now)
- 🧪 Ready-to-run example with pre-configured security settings
- 📦 Modular architecture (each strategy in its own Maven module)
- ⚙️ Easy to extend for database-backed or OAuth2 integrations
- 📘 **Swagger UI** (via SpringDoc OpenAPI)

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot 3**
- **Spring Security**
- **JWT (via `jjwt`)**
- **Maven**
- **SpringDoc OpenAPI** (for Swagger UI)

---

## 🚀 Getting Started

Each module is a self-contained Spring Boot application. You can run them individually:

---

### 1. Clone the Repository

```bash
git clone https://github.com/MrAasoo/spring_security.git
cd basic_auth
mvn spring-boot:run

