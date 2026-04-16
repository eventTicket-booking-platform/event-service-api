package com.ec7205.event_hub.event_service_api.service.impl;

import com.ec7205.event_hub.event_service_api.dto.request.CreateEventRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateEventRequest;
import com.ec7205.event_hub.event_service_api.dto.request.UpdateEventStatusRequest;
import com.ec7205.event_hub.event_service_api.dto.response.AdminEventSummaryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.ApiMessageResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventDetailResponse;
import com.ec7205.event_hub.event_service_api.dto.response.EventSummaryResponse;
import com.ec7205.event_hub.event_service_api.dto.response.paginate.AdminEventPaginateResponseDto;
import com.ec7205.event_hub.event_service_api.dto.response.paginate.EventPaginateResponseDto;
import com.ec7205.event_hub.event_service_api.entity.Category;
import com.ec7205.event_hub.event_service_api.entity.Event;
import com.ec7205.event_hub.event_service_api.entity.EventBanner;
import com.ec7205.event_hub.event_service_api.entity.TicketType;
import com.ec7205.event_hub.event_service_api.exception.BadRequestException;
import com.ec7205.event_hub.event_service_api.exception.ConflictException;
import com.ec7205.event_hub.event_service_api.exception.InvalidStatusTransitionException;
import com.ec7205.event_hub.event_service_api.exception.ResourceNotFoundException;
import com.ec7205.event_hub.event_service_api.mapper.EventMapper;
import com.ec7205.event_hub.event_service_api.repository.CategoryRepository;
import com.ec7205.event_hub.event_service_api.repository.EventBannerRepository;
import com.ec7205.event_hub.event_service_api.repository.EventRepository;
import com.ec7205.event_hub.event_service_api.service.EventService;
import com.ec7205.event_hub.event_service_api.service.FileService;
import com.ec7205.event_hub.event_service_api.utils.CommonFileSavedBinaryDataDto;
import com.ec7205.event_hub.event_service_api.utils.FileDataExtractor;
import com.ec7205.event_hub.event_service_api.utils.enums.EventStatus;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private static final Set<EventStatus> MANAGEABLE_EVENT_STATUSES =
            EnumSet.of(EventStatus.DRAFT, EventStatus.PUBLISHED, EventStatus.CANCELLED);

    private final EventRepository eventRepository;
    private final EventBannerRepository eventBannerRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final FileService fileService;
    private final FileDataExtractor fileDataExtractor;

    @Value("${bucketName}")
    private String bucketName;

    private static final String BANNER_DIRECTORY = "banner/";

    @Override
    public ApiMessageResponse createEvent(CreateEventRequest request, MultipartFile bannerimg) {
        validateEventSchedule(request.getStartDateTime(), request.getEndDateTime());
        validateTicketTypes(request.getTicketTypes().size());

        Category category = getActiveCategoryOrThrow(request.getCategoryId());

        Event event = Event.builder()
                .title(request.getTitle().trim())
                .description(request.getDescription().trim())
                .category(category)
                .venue(eventMapper.toVenueEntity(request.getVenue()))
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                .status(request.getStatus() == null ? EventStatus.DRAFT : request.getStatus())
                .createdBy(request.getCreatedBy())
                .build();

        List<TicketType> ticketTypes = eventMapper.toTicketTypeEntities(request.getTicketTypes(), event);
        event.getTicketTypes().addAll(ticketTypes);

        Event savedEvent = eventRepository.save(event);

        if (hasBannerFile(bannerimg)) {
            CommonFileSavedBinaryDataDto resource = uploadBanner(bannerimg);
            try {
                saveBannerMetadata(savedEvent, resource);
            } catch (SQLException | IOException ex) {
                cleanupCreatedResource(resource);
                throw new IllegalStateException("Failed to save event banner metadata", ex);
            }
        }

        return ApiMessageResponse.builder()
                .message("Event created successfully")
                .resourceId(savedEvent.getEvent_id())
                .build();
    }

    @Override
    public ApiMessageResponse updateEvent(Long eventId, UpdateEventRequest request, MultipartFile bannerimg) {
        validateEventSchedule(request.getStartDateTime(), request.getEndDateTime());
        validateTicketTypes(request.getTicketTypes().size());

        Event event = getDetailedEventOrThrow(eventId);
        ensureEventCanBeModified(event);

        Category category = getActiveCategoryOrThrow(request.getCategoryId());

        event.setTitle(request.getTitle().trim());
        event.setDescription(request.getDescription().trim());
        event.setCategory(category);
        eventMapper.updateVenue(event.getVenue(), request.getVenue());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());

        if (hasBannerFile(bannerimg)) {
            replaceEventBanner(event, bannerimg);
        }

        // TODO validate sold-ticket quantities before allowing ticket structure changes.
        event.getTicketTypes().clear();
        event.getTicketTypes().addAll(eventMapper.toTicketTypeEntities(request.getTicketTypes(), event));

        return ApiMessageResponse.builder()
                .message("Event updated successfully")
                .resourceId(event.getEvent_id())
                .build();
    }

    @Override
    public ApiMessageResponse changeEventStatus(Long eventId, UpdateEventStatusRequest request) {
        Event event = getEventOrThrow(eventId);
        validateStatusTransition(event.getStatus(), request.getStatus());

        event.setStatus(request.getStatus());
        return ApiMessageResponse.builder()
                .message("Event status updated successfully")
                .resourceId(event.getEvent_id())
                .build();
    }

    @Override
    public ApiMessageResponse deleteEvent(Long eventId) {
        Event event = getEventOrThrow(eventId);

        if (event.getStatus() == EventStatus.COMPLETED) {
            throw new ConflictException("Completed events cannot be deactivated");
        }

        event.setStatus(EventStatus.CANCELLED);
        return ApiMessageResponse.builder()
                .message("Event deactivated successfully")
                .resourceId(event.getEvent_id())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public EventPaginateResponseDto getPublicEvents(String search, Long categoryId, String city, Pageable pageable) {
        Specification<Event> specification = Specification.where(hasStatus(EventStatus.PUBLISHED))
                .and(hasActiveCategory())
                .and(titleContains(search))
                .and(hasCategory(categoryId))
                .and(hasCity(city));

        Page<EventSummaryResponse> eventPage = eventRepository.findAll(specification, pageable)
                .map(eventMapper::toEventSummaryResponse);

        return EventPaginateResponseDto.builder()
                .dataList(eventPage.getContent())
                .dataCount(eventPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public EventDetailResponse getPublicEventDetails(Long eventId) {
        Event event = getDetailedEventOrThrow(eventId);
        if (event.getStatus() != EventStatus.PUBLISHED || !Boolean.TRUE.equals(event.getCategory().getIsActive())) {
            throw new ResourceNotFoundException("Published event not found for id: " + eventId);
        }
        return eventMapper.toEventDetailResponse(event);
    }

    @Override
    @Transactional(readOnly = true)
    public EventDetailResponse getAdminEventDetails(Long eventId) {
        Event event = getDetailedEventOrThrow(eventId);
        return eventMapper.toEventDetailResponse(event);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminEventPaginateResponseDto getAdminEvents(String search, EventStatus status, Pageable pageable) {
        Specification<Event> specification = Specification.where(titleContains(search))
                .and(hasStatus(status));

        Page<AdminEventSummaryResponse> eventPage = eventRepository.findAll(specification, pageable)
                .map(eventMapper::toAdminEventSummaryResponse);

        return AdminEventPaginateResponseDto.builder()
                .dataList(eventPage.getContent())
                .dataCount(eventPage.getTotalElements())
                .build();
    }

    private Event getEventOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for id: " + eventId));
    }

    private Event getDetailedEventOrThrow(Long eventId) {
        return eventRepository.findDetailedById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for id: " + eventId));
    }

    private Category getActiveCategoryOrThrow(Long categoryId) {
        return categoryRepository.findByIdAndIsActiveTrue(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Active category not found for id: " + categoryId));
    }

    private void validateEventSchedule(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (!endDateTime.isAfter(startDateTime)) {
            throw new BadRequestException("Event end date time must be after start date time");
        }
    }

    private void validateTicketTypes(int ticketTypeCount) {
        if (ticketTypeCount < 1) {
            throw new BadRequestException("At least one ticket type is required");
        }
    }

    private void ensureEventCanBeModified(Event event) {
        if (!MANAGEABLE_EVENT_STATUSES.contains(event.getStatus())) {
            throw new ConflictException("Only draft, published, or cancelled events can be updated");
        }
    }

    private void validateStatusTransition(EventStatus currentStatus, EventStatus targetStatus) {
        if (currentStatus == targetStatus) {
            return;
        }

        boolean allowed = switch (currentStatus) {
            case DRAFT -> EnumSet.of(EventStatus.PUBLISHED, EventStatus.CANCELLED).contains(targetStatus);
            case PUBLISHED -> EnumSet.of(EventStatus.CANCELLED, EventStatus.COMPLETED).contains(targetStatus);
            case CANCELLED -> targetStatus == EventStatus.PUBLISHED;
            case COMPLETED -> false;
        };

        if (!allowed) {
            throw new InvalidStatusTransitionException(
                    "Invalid event status transition from " + currentStatus + " to " + targetStatus
            );
        }
    }

    private Specification<Event> titleContains(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + search.trim().toLowerCase(Locale.ROOT) + "%"
            );
        };
    }

    private Specification<Event> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.join("category", JoinType.INNER).get("id"), categoryId);
        };
    }

    private Specification<Event> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.join("venue", JoinType.INNER).get("city")),
                    city.trim().toLowerCase(Locale.ROOT)
            );
        };
    }

    private Specification<Event> hasStatus(EventStatus status) {
        return (root, query, criteriaBuilder) -> status == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("status"), status);
    }

    private Specification<Event> hasActiveCategory() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(
                root.join("category", JoinType.INNER).get("isActive")
        );
    }

    private boolean hasBannerFile(MultipartFile bannerimg) {
        return bannerimg != null && !bannerimg.isEmpty();
    }

    private CommonFileSavedBinaryDataDto uploadBanner(MultipartFile bannerimg) {
        return fileService.createResource(bannerimg, BANNER_DIRECTORY, bucketName);
    }

    private void replaceEventBanner(Event event, MultipartFile bannerimg) {
        CommonFileSavedBinaryDataDto resource = null;
        EventBanner existingBanner = eventBannerRepository.findByEventId(event.getEvent_id()).orElse(null);

        try {
            if (existingBanner != null) {
                fileService.deleteResource(
                        bucketName,
                        fileDataExtractor.byteArrayToString(existingBanner.getDirectory()),
                        fileDataExtractor.byteArrayToString(existingBanner.getFileName())
                );
            }

            resource = uploadBanner(bannerimg);

            if (existingBanner == null) {
                saveBannerMetadata(event, resource);
                return;
            }

            existingBanner.setCreatedDate(new Date());
            existingBanner.setDirectory(resource.getDirectory().getBytes());
            existingBanner.setFileName(fileDataExtractor.blobToByteArray(resource.getFileName()));
            existingBanner.setHash(fileDataExtractor.blobToByteArray(resource.getHash()));
            existingBanner.setResourceUrl(fileDataExtractor.blobToByteArray(resource.getResourceUrl()));
            existingBanner.setEvent(event);
            eventBannerRepository.save(existingBanner);
        } catch (Exception ex) {
            cleanupCreatedResource(resource);
            throw new IllegalStateException("Failed to update event banner", ex);
        }
    }

    private void saveBannerMetadata(Event event, CommonFileSavedBinaryDataDto resource) throws SQLException, IOException {
        EventBanner eventBanner = EventBanner.builder()
                .propertyId(UUID.randomUUID().toString())
                .directory(resource.getDirectory().getBytes())
                .fileName(fileDataExtractor.blobToByteArray(resource.getFileName()))
                .resourceUrl(fileDataExtractor.blobToByteArray(resource.getResourceUrl()))
                .hash(fileDataExtractor.blobToByteArray(resource.getHash()))
                .createdDate(new Date())
                .event(event)
                .build();

        EventBanner persistedBanner = eventBannerRepository.save(eventBanner);
        event.setEventBanner(persistedBanner);
    }

    private void cleanupCreatedResource(CommonFileSavedBinaryDataDto resource) {
        if (resource == null) {
            return;
        }

        try {
            fileService.deleteResource(
                    bucketName,
                    resource.getDirectory(),
                    fileDataExtractor.extractActualFileName(new InputStreamReader(resource.getFileName().getBinaryStream()))
            );
        } catch (Exception ignored) {
        }
    }

}
