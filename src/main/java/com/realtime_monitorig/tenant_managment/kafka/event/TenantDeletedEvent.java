package com.realtime_monitorig.tenant_managment.kafka.event;

import java.util.UUID;

public record TenantDeletedEvent(
    DomainEvent event,
        UUID id
) {}
