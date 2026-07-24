package com.realtime_monitorig.tenant_managment.kafka.event;

import java.util.UUID;

public record TenantCreatedEvent(
    UUID id,
    DomainEvent even,
   
    String name,
    String companyName,
    String email,
    String phone,
    String status
) {}
