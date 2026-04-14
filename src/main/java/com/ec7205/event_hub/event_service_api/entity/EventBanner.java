package com.ec7205.event_hub.event_service_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "event_banner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventBanner {
    @Id
    @Column(name = "property_id", nullable = false, length = 80)
    private String propertyId;

    @Lob
    @Column(name = "directory", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] directory;

    @Lob
    @Column(name = "file_name", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] fileName;

    @Lob
    @Column(name = "resource_url", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] resourceUrl;

    @Lob
    @Column(name = "hash", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] hash;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, columnDefinition = "DATETIME")
    private Date createdDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false, unique = true)
    private Event event;
}
