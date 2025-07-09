# Identity Service

Spring Boot identity and authentication service with role-based access control.

## Quick Start

### Using Docker Compose (Recommended)

```bash
# Build
mvn clean package -DskipTests

# Run with Docker
docker-compose up -d

# Or run locally
mvn spring-boot:run
```

## API Endpoints

- `POST /auth/login` - Login
- `POST /auth/register` - Register
- `GET /users` - Get users (ADMIN only)
- `GET /roles` - Get roles
- `GET /permissions` - Get permissions

## Default Data

- **Admin**: username: `admin`, password: `123456`
- **Roles**: `USER`, `ADMIN`

## Environment

- **App**: http://localhost:8080
- **Database**: localhost:3307 (root/123456)

## Requirements

- Java 17+
- Maven 3.6+
- Docker (optional) 