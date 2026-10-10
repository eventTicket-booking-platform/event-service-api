# Event Service API

## Ticket inventory and edits

New tiers initialize `availableQuantity = totalQuantity`, including zero.
Public detail and booking-info ticket responses retain `totalQuantity` and now
also expose `availableQuantity`. Deploy this API before the updated customer UI.

For `PUT /event-service/api/v1/events/{eventId}`, existing tiers must submit their
response `ticketTypeId` as request `id`; missing/null `id` means a new tier.
Existing IDs remain stable. Under event and ticket-row write locks, the service
calculates committed tickets as `totalQuantity - availableQuantity` (including
unpaid reservations). New availability is `newTotal - committed`.
Totals below committed quantity, removal of committed tiers, duplicate IDs and
foreign IDs return 409. Omitted tiers with no committed inventory may be removed.
All validation and changes run in one local transaction. Clients that omit IDs
cannot replace sold tiers; update the dashboard alongside this change.

`TicketInventoryLifecycleTest` checks initialization, public inventory after a
reservation, safe increases/decreases, stable IDs, additions/removals, invalid
IDs and transaction rollback. Run `mvn test`; these tests use H2, not MySQL.

## Idempotent ticket release

The existing `POST /event-service/api/v1/internal/events/{eventId}/release`
accepts an optional `releaseId` (at most 100 characters) alongside `tickets`.
Booking Service uses its booking reference for both immediate compensation and
scheduled expiry retries. Event Service locks the event row and stores the
release ID, event ID and canonical ticket quantities in `ticket_releases`, in the
same local transaction as all inventory increments. Repeating the ID with the
same event/quantities returns success without changing inventory; reusing it
with different event/quantities returns a conflict. A failed inventory update
rolls back both the increments and receipt. Requests without `releaseId` retain
their existing behavior and are not deduplicated.

Deploy this support before Booking Service's reservation-expiry version. With
`ddl-auto=update` the table is created automatically. For manually managed schemas:

```sql
CREATE TABLE ticket_releases (
    release_id VARCHAR(100) NOT NULL PRIMARY KEY,
    event_id BIGINT NOT NULL,
    ticket_quantities TEXT NOT NULL
);
```

Retain receipts while callers might retry; deleting one permits another release.
The lock serializes releases for the same event. This is local HTTP-operation
idempotency, not a distributed transaction with Booking Service.
`TicketReleaseTest` checks repeat/concurrent release, changed quantities, atomic
rollback/retry and callers without IDs using H2. Run with `mvn test` on Java 17+.

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

- ADMIN: all event management and category management.
- HOST: create events; update, cancel, change status and read management details
  only for events whose `createdBy` matches the JWT subject. List filtering and
  counts are scoped in the database before pagination.
- CUSTOMER: public published-event/category reads; no management operations.
- Authenticated non-owners receive 403; unauthenticated management uses the
  existing 401 handling. Upper/lowercase realm roles use lowercase authorities.

Creation always uses the JWT subject for `createdBy`; client-supplied values are
ignored. `GET /events/admin/owned-ids` requires ADMIN/HOST and returns IDs owned
by the caller, for Booking Service's scoped queries. The existing internal
booking-info response includes `createdBy` for booking-detail authorization.
No cross-service database joins or foreign keys are introduced.

Deploy this version before Booking Service. Audit historical `createdBy` values:
older versions trusted client input, so ownership may be incorrect or forged.
No owners are guessed or reassigned automatically. The gateway rejects external
access to Event Service's internal inventory routes and Booking Service's internal
count route. Internal inventory APIs still trust the service network; direct
backend ports must remain private. Notification administration is ADMIN-only at
the gateway; Notification Service itself still depends on that network boundary.

`EventOwnershipTest` verifies persisted scope/counts, forged creation ownership,
ADMIN access, owner/non-owner HOST operations and CUSTOMER denial.
`EventAuthorizationHttpTest` verifies JWT conversion, HTTP 401/403 and ADMIN-only
category management. Run `mvn test` with Java 17+ (persistence tests use H2).

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

## How to run integration tests

Run `mvn test` for the existing tests without Docker. With Docker running Linux containers, run `mvn verify -Pintegration-tests` to also run the opt-in `*IT` Testcontainers tests. Images are downloaded on first use; connection addresses and ports come from the containers. The integration profile fails if Docker is unavailable.

MySQL coverage: 20 concurrent reservation attempts against stock of 10, with exactly 10 successes and no negative inventory.
