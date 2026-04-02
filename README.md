# FLC-Herts

Furzefield Leisure Centre (FLC) Booking System - A Spring Boot REST API application for managing group exercise lesson bookings.

## Architecture Overview

The system consists of two components:

### Backend (Spring Boot REST API)
- **Framework**: Spring Boot 3.2.0
- **Database**: PostgreSQL
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven

### Frontend (React)
- **Framework**: React
- **Location**: `/frontend`

## Prerequisites

### For Backend
- Java 17 or higher
- Maven 3.8.0 or higher
- PostgreSQL 12 or higher
- Git

### For Frontend
- Node.js 14 or higher
- npm 6 or higher

## Backend Setup

### 1. Database Setup

Create PostgreSQL database:
```bash
createdb flc_booking_db
```

Or using psql:
```sql
CREATE DATABASE flc_booking_db;
```

Default credentials (update in application.properties if different):
- Username: `postgres`
- Password: `postgres`
- Host: `localhost`
- Port: `5432`

### 2. Build Backend

```bash
cd backend
mvn clean install
```

### 3. Run Backend

```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

The application will automatically:
- Create all database tables
- Initialize 384 sample lessons (8 weekends × 2 days × 3 time slots × 8 exercise types)
- Create 10 sample members

## API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Health & Statistics
- `GET /health` - System health status
- `GET /health/stats` - System statistics

### Members
- `GET /members` - Get all members
- `GET /members/{id}` - Get member by ID
- `GET /members/email/{email}` - Get member by email
- `POST /members` - Register new member
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com"
  }
  ```
- `DELETE /members/{id}` - Delete member

### Lessons
- `GET /lessons` - Get all lessons
- `GET /lessons/{id}` - Get lesson by ID
- `GET /lessons/weekend/{weekendNumber}` - Get lessons for weekend
- `GET /lessons/day/{day}` - Get lessons for day (SATURDAY/SUNDAY)
- `GET /lessons/exercise/{exerciseName}` - Get lessons by exercise
- `GET /lessons/available` - Get available lessons
- `GET /lessons/range?startWeekend=1&endWeekend=8` - Get lessons for range
- `POST /lessons` - Create new lesson
  ```json
  {
    "exerciseType": "YOGA",
    "day": "SATURDAY",
    "timeSlot": "MORNING",
    "weekendNumber": 1
  }
  ```

### Bookings
- `GET /bookings` - Get all bookings
- `GET /bookings/{id}` - Get booking by ID
- `GET /bookings/member/{memberId}` - Get member's bookings
- `GET /bookings/lesson/{lessonId}` - Get lesson's bookings
- `POST /bookings` - Book a lesson
  ```json
  {
    "memberId": 1,
    "lessonId": 1
  }
  ```
- `PUT /bookings/{id}/cancel` - Cancel booking

### Reviews
- `POST /reviews` - Add review for lesson
  ```json
  {
    "memberId": 1,
    "lessonId": 1,
    "rating": 5,
    "comment": "Great class!"
  }
  ```
- `GET /reviews/lesson/{lessonId}` - Get lesson reviews
- `GET /reviews/lesson/{lessonId}/average-rating` - Get average rating

## Data Model

### Member
- `memberId` (Long, Primary Key)
- `name` (String)
- `email` (String, Unique)
- `bookings` (List<Booking>)

### Lesson
- `lessonId` (Long, Primary Key)
- `exerciseType` (Enum: YOGA, ZUMBA, AQUACISE, BOX_FIT, BODY_BLITZ, PILATES, SPIN, HIIT)
- `day` (Enum: SATURDAY, SUNDAY)
- `timeSlot` (Enum: MORNING, AFTERNOON, EVENING)
- `weekendNumber` (Integer)
- `maxCapacity` (Integer, default: 4)
- `bookings` (List<Booking>)
- `reviews` (List<Review>)

### Booking
- `bookingId` (Long, Primary Key)
- `member` (ManyToOne: Member)
- `lesson` (ManyToOne: Lesson)
- `bookingDate` (LocalDateTime)
- `cancelled` (Boolean)

### Review
- `reviewId` (Long, Primary Key)
- `member` (ManyToOne: Member)
- `lesson` (ManyToOne: Lesson)
- `rating` (Integer: 1-5)
- `comment` (String)
- `reviewDate` (LocalDateTime)

## Exercise Types & Pricing

| Exercise | Price |
|----------|-------|
| YOGA | $25.00 |
| ZUMBA | $30.00 |
| AQUACISE | $28.00 |
| BOX_FIT | $35.00 |
| BODY_BLITZ | $32.00 |
| PILATES | $26.00 |
| SPIN | $31.00 |
| HIIT | $33.00 |

## Configuration

Edit `backend/src/main/resources/application.properties` to customize:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/flc_booking_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Server
server.port=8080
server.servlet.context-path=/api

# Logging
logging.level.root=INFO
logging.level.com.flc=DEBUG
```

## Build & Run with Maven

### Clean Build
```bash
mvn clean install
```

### Run Tests
```bash
mvn test
```

### Package Application
```bash
mvn package
```

### Run JAR
```bash
java -jar target/flc-booking-system-1.0.0.jar
```

## Troubleshooting

### Database Connection Error
1. Ensure PostgreSQL is running
2. Check database name: `flc_booking_db`
3. Verify credentials in `application.properties`
4. Create database if not exists: `createdb flc_booking_db`

### Port Already in Use
```bash
# Change port in application.properties
server.port=8081
```

### Hibernate Schema Creation Issues
```properties
# Reset database (WARNING: will delete all data)
spring.jpa.hibernate.ddl-auto=create-drop
```

## Frontend Setup

```bash
cd frontend
npm install
npm start
```

Frontend runs on `http://localhost:3000`

## API Testing

### Using cURL
```bash
# Get all members
curl http://localhost:8080/api/members

# Register member
curl -X POST http://localhost:8080/api/members \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@example.com"}'

# Book a lesson
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{"memberId":1,"lessonId":1}'
```

### Using Postman
1. Import the API endpoints listed above
2. Set base URL: `http://localhost:8080/api`
3. Test each endpoint

## License

Proprietary - Furzefield Leisure Centre

## Contributors

- Development Team: Hassam025
