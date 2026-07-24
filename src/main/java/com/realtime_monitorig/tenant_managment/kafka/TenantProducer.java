package com.realtime_monitorig.tenant_managment.kafka;

import com.realtime_monitorig.tenant_managment.kafka.event.TenantCreatedEvent;
import com.realtime_monitorig.tenant_managment.kafka.event.TenantUpdatedEvent;
import com.realtime_monitorig.tenant_managment.kafka.event.DomainEvent;
import com.realtime_monitorig.tenant_managment.entity.Tenant;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTenantCreation(Tenant tenant) {
        DomainEvent event = new DomainEvent(
            UUID.randomUUID(),
            "TENANT_CREATED",
            tenant.getId(),
            "TENANT",
            Instant.now()
        );
        TenantCreatedEvent tenantEvent = new TenantCreatedEvent(
            event,
            tenant.getName(),
            tenant.getCompanyName(),
            tenant.getEmail(),
            tenant.getPhone(),
            tenant.getStatus() != null ? tenant.getStatus().name() : null
        );
        log.info("sending tenant creation event: {}", tenantEvent);
        kafkaTemplate.send("tenant-events-v4", tenant.getId().toString(), tenantEvent);
    }

    public void sendTenantUpdate(Tenant tenant) {
        DomainEvent event = new DomainEvent(
            UUID.randomUUID(),
            "TENANT_UPDATED",
            tenant.getId(),
            "TENANT",
            Instant.now()
        );
        TenantUpdatedEvent tenantEvent = new TenantUpdatedEvent(
            event,
            tenant.getName(),
            tenant.getCompanyName(),
            tenant.getEmail(),
            tenant.getPhone(),
            tenant.getStatus() != null ? tenant.getStatus().name() : null
        );

        log.info("sending tenant update event: {}", tenantEvent);
        kafkaTemplate.send("tenant-events-v4", tenant.getId().toString(), tenantEvent);
    }
}
