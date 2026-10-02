package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.TicketType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

    @Query("select tt from TicketType tt where tt.event.id = :eventId order by tt.id asc")
    List<TicketType> findByEventIdOrderByIdAsc(@Param("eventId") Long eventId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
        update TicketType tt
        set tt.availableQuantity = tt.availableQuantity - :quantity
        where tt.id = :ticketTypeId
        and tt.availableQuantity >= :quantity
    """)
    int reserveTickets(
            @Param("ticketTypeId") Long ticketTypeId,
            @Param("quantity") Integer quantity
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
        update TicketType tt
        set tt.availableQuantity = tt.availableQuantity + :quantity
        where tt.id = :ticketTypeId
            and tt.availableQuantity + :quantity <= tt.totalQuantity
    """)
    int releaseTickets(
            @Param("ticketTypeId") Long ticketTypeId,
            @Param("quantity") Integer quantity
    );
}