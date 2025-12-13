# Orbis 🌍

A location-based social media REST API built with Spring Boot. Orbis enables users to create, discover, and interact with posts based on geographic proximity.

## Features

- **Location-Based Posts**: Create posts tied to geographic coordinates and discover nearby content
- **JWT Authentication**: Secure user registration and login with stateless JWT tokens
- **Voting System**: Upvote and downvote posts for community-driven content curation
- **Comments**: Engage with posts through a commenting system
- **Rate Limiting**: Built-in request throttling to prevent abuse (20 requests/minute per IP)
- **Geospatial Queries**: Efficient nearby post discovery using PostGIS spatial functions

## Tech Stack

| Component | Technology |
|-----------|------------|
| **Framework** | Spring Boot 3.5.4 |
| **Language** | Java 21 |
| **Database** | PostgreSQL with PostGIS |
| **ORM** | Spring Data JPA + Hibernate Spatial |
| **Security** | Spring Security + JWT (jjwt 0.11.5) |
| **Geospatial** | JTS Core + Hibernate Spatial |
| **Build Tool** | Maven |
| **Containerization** | Docker + Docker Compose |

## Project Structure

```
src/main/java/com/strink/orbis/
├── OrbisApplication.java      # Application entry point
├── advice/                    # Global exception handlers
├── aspect/                    # AOP logging aspects
├── config/                    # Security & JWT configuration
├── dto/                       # Data Transfer Objects
├── exception/                 # Custom exceptions
├── filter/                    # Request filters (rate limiting)
├── handler/                   # REST controllers
├── model/                     # JPA entities
├── repository/                # Spring Data repositories
└── service/                   # Business logic layer
```

## Prerequisites

- **Java 21** or higher
- **Maven 3.8+**
- **PostgreSQL 12+** with PostGIS extension enabled
- A running PostgreSQL instance with a database configured

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd orbis
```

### 2. Database Setup

Ensure PostgreSQL is running and create a database with PostGIS:

```sql
CREATE DATABASE orbis;
\c orbis
CREATE EXTENSION IF NOT EXISTS postgis;
```

### 3. Configure Application Properties

Create or update `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/orbis
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.spatial.dialect.postgis.PostgisPG10Dialect

# JWT Configuration
jwt.secret=your-256-bit-secret-key
jwt.expiration=86400000
```

### 4. Build and Run

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using Maven directly
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### 5. Running with Docker (Alternative)

If you prefer using Docker, you can run the entire stack with Docker Compose:

**Prerequisites:**
- Docker and Docker Compose installed

**Steps:**

```bash
# Build the application JAR first
./mvnw clean package -DskipTests

# Start all services (PostgreSQL with PostGIS + App)
docker compose up -d

# View logs
docker compose logs -f

# Stop services
docker compose down
```

The API will be available at `http://localhost:8081`

> **Note:** The Docker setup uses:
> - PostgreSQL with PostGIS on port `5433` (mapped from container's `5432`)
> - Application on port `8081` (mapped from container's `8080`)
> - Persistent volume `db_data` for database storage

## API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/auth/register` | Register a new user | No |
| `GET` | `/api/auth/login` | Login and get JWT token | No |

**Request Body (register/login):**
```json
{
  "username": "johndoe",
  "password": "securepassword"
}
```

---

### Posts

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/posts` | Create a new post | Yes |
| `GET` | `/api/posts/{postId}` | Get post by ID | Yes |
| `GET` | `/api/users/{userId}/posts` | Get all posts by user | Yes |
| `GET` | `/api/posts/nearby` | Get posts within radius | Yes |
| `DELETE` | `/api/posts/{postId}` | Delete a post | Yes |

**Create Post Request:**
```json
{
  "title": "Beautiful sunset!",
  "caption": "Captured this amazing view today",
  "position": {
    "latitude": 37.7749,
    "longitude": -122.4194
  }
}
```

**Get Nearby Posts Request:**
```json
{
  "position": {
    "latitude": 37.7749,
    "longitude": -122.4194
  },
  "radius": 5
}
```
> Note: `radius` is in kilometers

---

### Comments

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/posts/{postId}/comments` | Add comment to post | Yes |
| `DELETE` | `/api/comments/{commentId}` | Delete a comment | Yes |

**Add Comment Request:**
```json
{
  "content": "Great post!"
}
```

---

### Votes

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/{postId}/vote` | Vote on a post | Yes |

**Vote Request:**
```json
{
  "voteType": "UPVOTE"
}
```
> Valid vote types: `UPVOTE`, `DOWNVOTE`

## Authentication

All endpoints (except `/api/auth/**`) require a valid JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Error Handling

The API returns consistent error responses:

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Post not found with id: abc123",
  "path": "/api/posts/abc123"
}
```

| Status Code | Description |
|-------------|-------------|
| `400` | Bad Request - Validation errors |
| `401` | Unauthorized - Invalid/missing JWT |
| `403` | Forbidden - Insufficient permissions |
| `404` | Not Found - Resource doesn't exist |
| `409` | Conflict - Duplicate resource |
| `429` | Too Many Requests - Rate limit exceeded |
| `500` | Internal Server Error |

## Rate Limiting

The API implements IP-based rate limiting:
- **Limit**: 20 requests per minute per IP address
- **Response**: HTTP 429 with message when exceeded

## Development

### Running Tests

```bash
./mvnw test
```

### Building for Production

```bash
./mvnw clean package -DskipTests
java -jar target/orbis-0.0.1-SNAPSHOT.jar
```

## Data Models

### Post
- `id` - UUID (auto-generated)
- `userId` - Reference to creating user
- `title` - Post title
- `caption` - Post content
- `location` - Geographic point (PostGIS geometry)
- `upvoteCount` / `downvoteCount` - Vote counters
- `commentCount` - Number of comments
- `createdAt` / `updatedAt` - Timestamps

### User
- `id` - UUID (auto-generated)
- `username` - Unique username
- `password` - Hashed password
- `createdAt` - Registration timestamp

### Comment
- `id` - UUID (auto-generated)
- `postId` - Reference to parent post
- `userId` - Reference to commenting user
- `content` - Comment text
- `createdAt` / `updatedAt` - Timestamps

### Vote
- `id` - UUID (auto-generated)
- `postId` - Reference to voted post
- `userId` - Reference to voting user
- `voteType` - UPVOTE or DOWNVOTE
- `createdAt` - Vote timestamp

## License

This project is currently unlicensed.

---
