# 🔐 Spring Security Auth Modules

A modular Spring Boot project showcasing multiple authentication strategies using **Spring Security**, including:

- 🔒 Basic Authentication
- 🔑 JWT-based Authentication
- 🔗 OAuth2 Login (Google, GitHub)

Each module is self-contained, production-ready, and designed to be extensible for real-world applications.

---

## 📦 Modules

| Module Name                | Type        | Description                                                                 |
|----------------------------|-------------|-----------------------------------------------------------------------------|
| `basic_auth`               | Basic Auth  | Single hardcoded user via `application.properties`.                         |
| `basic_auth_in_memory_user`| Basic Auth  | In-memory `UserDetailsService` for multiple users.                          |
| `basic_auth_db_user`       | Basic Auth  | Users and roles stored in a relational database.                            |
| `jwt_auth_in_memory_user`  | JWT         | Stateless token-based auth with in-memory users.                            |
| `jwt_auth_db_user`         | JWT         | JWT with persistent user storage and database roles.                        |
| `oauth2`                   | OAuth2      | Login via `Google` or `GitHub` using Spring Security OAuth2.                    |

---

## ⚙️ Tech Stack

> - Java 17+
> - Spring Boot 3
> - Spring Security
> - OAuth2 Client (Google, GitHub)
> - JWT (via `jjwt`)
> - SpringDoc OpenAPI (Swagger)
> - Maven

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/MrAasoo/spring_security.git
cd spring_security
```

### 2. Run a specific module

Each module is a standalone Spring Boot app. Choose and run one:

```bash
cd <module_name>
mvn spring-boot:run
```
Replace *module_name* with any module name like jwt_auth_db_user, oauth2, etc.

---

## 🔧 Environment Setup
Some modules (like oauth2) require environment variables.

### Sample .env file
```dotenv
# OAuth2
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
GITHUB_CLIENT_ID=your-github-client-id
GITHUB_CLIENT_SECRET=your-github-client-secret
```
- You can place .env in the project root and load it via your IDE or custom Java code.

---

## 📖 Documentation

Each module has its own documentation Swagger UI is available at:

```bash
http://localhost:{module_server_port}/swagger-ui.html
```

Replace `{module_server_port}` with the port configured for the specific module can be found in `application.properties`.

---

## 🤝 Contributing

- Pull requests and issue reports are welcome. Feel free to fork, extend, and share feedback.

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.