package com.realtime_monitorig.tenant_managment.kafka.event;

import java.util.UUID;

public record TenantUpdatedEvent(
    DomainEvent event,
    String name,
    String companyName,
    String email,
    String phone,
    String status
) {}
