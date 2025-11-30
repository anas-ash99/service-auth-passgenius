# Auth Service - PassGenius

Authentication service for PassGenius, handling user login, registration, and token management.

## Architecture

This service is built using **Java 21** and **Spring Boot 3.3.4**.

### Tech Stack

*   **Framework**: Spring Boot 3.3.4 (Web, Data MongoDB, Test)
*   **Language**: Java 21
*   **Database**: MongoDB
*   **Security**:
    *   `jjwt` (0.11.5) for JSON Web Token handling.
    *   `jbcrypt` (0.4) for password hashing.

### Key Features

*   User authentication and authorization.
*   JWT generation and validation.
*   Secure password handling using BCrypt.
