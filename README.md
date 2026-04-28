# Event Service API

Catalog and event-management service for Event Hub. This service owns categories, public event discovery, event details, admin event CRUD, and internal event data required by booking flows.

## Stack

- Java 17
- Spring Boot 3
- Spring Security OAuth2 resource server
- Spring Data JPA
- Spring Cloud Config client
- Eureka client
- MySQL
- AWS S3

## Functional Requirements

- Publish public event listings
- Filter public events by search, category, and city
- Return public event details with venue and ticket tiers
- Return active categories for public browsing
- Create events with multipart payload and optional banner image
- Update events and ticket tiers
- Change event status
- Delete events
- Return admin event detail and paginated admin event list
- Expose internal booking metadata to other services

## Non-Functional Requirements

- Stateless JWT authorization for admin and host actions
- Public read endpoints without authentication
- Centralized configuration through Config Server
- Eureka registration for discovery
- MySQL persistence
- S3-backed banner storage
- Validation on create and update requests
- Actuator health support

## APIs

Base path: `/event-service/api/v1`

Public endpoints:

- `GET /events`
- `GET /events/{eventId}`
- `GET /events/categories`

Admin or host endpoints:

- `POST /events`
- `PUT /events/{eventId}`
- `PATCH /events/{eventId}/status`
- `DELETE /events/{eventId}`
- `GET /events/admin/{eventId}`
- `GET /events/admin/all`
- `POST /categories`
- `PUT /categories/{categoryId}`
- `DELETE /categories/{categoryId}`

Internal endpoints:

- `GET /internal/events/{eventId}/booking-info`
- `GET /internal/events/{eventId}/exists`
- `GET /internal/events/{eventId}/ticket-types`

## Role-Based Access

- `permitAll`: public event reads and internal event endpoints
- `admin`, `host`: event write operations and category management

The service uses both `hasAnyAuthority` and `hasAnyRole` checks. Public GET access is also explicitly allowed in `SecurityConfig`.

## Runtime Dependencies

- Config Server on `8888`
- Eureka Server on `8761`
- MySQL
- AWS S3 credentials and bucket

## Local Setup

1. Copy `.env.example` to `.env`.
2. Fill:
   - `SPRING_CLOUD_CONFIG_URI`
   - `EVENT_DB_PASSWORD`
   - `AWS_ACCESS_KEY_ID`
   - `AWS_SECRET_ACCESS_KEY`
3. Start:
   - `config-server`
   - `eureka-server`
   - MySQL
4. Run:

```powershell
.\mvnw.cmd spring-boot:run
```

Default port: `9091`

## Build

```powershell
.\mvnw.cmd clean package
```

## Notes

- Event create and update use multipart form data with a `request` JSON part and optional `bannerimg` file.
- Internal endpoints are open inside the service and are meant for service-to-service use.
