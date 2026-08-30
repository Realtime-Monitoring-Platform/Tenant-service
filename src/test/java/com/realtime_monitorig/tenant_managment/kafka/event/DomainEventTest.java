package com.realtime_monitorig.tenant_managment.kafka.event;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventTest {

    @Test
    void recordFields() {
        UUID eventId = UUID.randomUUID();
        UUID aggregateId = UUID.randomUUID();
        Instant occurredAt = Instant.now();

        DomainEvent event = new DomainEvent(eventId, "TENANT_CREATED", aggregateId, "TENANT", occurredAt);

        assertThat(event.eventId()).isEqualTo(eventId);
        assertThat(event.eventType()).isEqualTo("TENANT_CREATED");
        assertThat(event.aggregateId()).isEqualTo(aggregateId);
        assertThat(event.aggregateType()).isEqualTo("TENANT");
        assertThat(event.occurredAt()).isEqualTo(occurredAt);
        assertThat(event.toString()).contains("TENANT_CREATED");
    }

    @Test
    void tenantCreatedEvent_record() {
        UUID id = UUID.randomUUID();
        DomainEvent event = new DomainEvent(UUID.randomUUID(), "TENANT_CREATED", id, "TENANT", Instant.now());
        TenantCreatedEvent e = new TenantCreatedEvent(id, event, "Acme", "Acme Inc",
                "a@a.com", "123", "ACTIVE", UUID.randomUUID());
        assertThat(e.id()).isEqualTo(id);
        assertThat(e.name()).isEqualTo("Acme");
        assertThat(e.status()).isEqualTo("ACTIVE");
    }

    @Test
    void tenantDeletedEvent_record() {
        UUID id = UUID.randomUUID();
        DomainEvent event = new DomainEvent(UUID.randomUUID(), "TENANT_DELETED", id, "TENANT", Instant.now());
        TenantDeletedEvent e = new TenantDeletedEvent(event, id);
        assertThat(e.id()).isEqualTo(id);
        assertThat(e.event().eventType()).isEqualTo("TENANT_DELETED");
    }

    @Test
    void tenantUpdatedEvent_record() {
        UUID id = UUID.randomUUID();
        DomainEvent event = new DomainEvent(UUID.randomUUID(), "TENANT_UPDATED", id, "TENANT", Instant.now());
        TenantUpdatedEvent e = new TenantUpdatedEvent(event, id, "Acme", "Acme Inc",
                "a@a.com", "123", "ACTIVE", UUID.randomUUID());
        assertThat(e.id()).isEqualTo(id);
        assertThat(e.event().eventType()).isEqualTo("TENANT_UPDATED");
        assertThat(e.name()).isEqualTo("Acme");
    }
}