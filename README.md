# Modern Bookstore — Spring Boot 3 + React 18

Modernized full-stack bookstore management system.

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.3, Java 17, Spring Security 6, JWT |
| Database | PostgreSQL 15 (JPA/Hibernate, ddl-auto: update) |
| Frontend | React 18, Vite 5, TypeScript 5 |
| State | TanStack Query v5, React Hook Form v7, Zod v3 |
| UI | Bootstrap 5, react-toastify |

## Prerequisites

- Java 17+, Maven 3.9+
- Node.js 20+, npm 10+
- PostgreSQL 15+ running on `localhost:5432`

## Quick Start

### 1. Ensure PostgreSQL is running with the `bookstore` database

```bash
psql -U postgre -h localhost -p 5432 -c "\l"
# If bookstore db does not exist:
psql -U postgre -h localhost -p 5432 -c "CREATE DATABASE bookstore;"
```

### 2. Run the Backend

```bash
cd backend
mvn spring-boot:run
```

- Starts at **http://localhost:8080**
- JPA auto-creates all tables on first run
- Seeds default users:
  - Admin: `admin` / `Admin123`
  - Employee: `employee1` / `Employee123`

### 3. Run the Frontend

```bash
cd frontend
npm install
npm run dev
```

- Starts at **http://localhost:5173**
- All `/api/*` requests are proxied to `:8080`

### 4. Open the App

Navigate to **http://localhost:5173**

Login with `admin` / `Admin123` to access all features.

## API Endpoints

| Method | Endpoint | Auth |
|---|---|---|
| POST | `/api/v1/auth/login` | Public |
| POST | `/api/v1/auth/register` | Public |
| POST | `/api/v1/feedback` | Public |
| GET | `/api/v1/books` | Authenticated |
| POST/PUT/DELETE | `/api/v1/books` | ADMIN |
| GET/POST/PUT/DELETE | `/api/v1/employees` | ADMIN |
| GET/POST/PUT | `/api/v1/purchases` | ADMIN + EMPLOYEE |
| DELETE | `/api/v1/purchases/{id}` | ADMIN |
| GET/DELETE | `/api/v1/feedback` | ADMIN |

## Default Users

| Username | Password | Role |
|---|---|---|
| `admin` | `Admin123` | ROLE_ADMIN |
| `employee1` | `Employee123` | ROLE_EMPLOYEE |
