package com.realtime_monitorig.tenant_managment.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TenantTest {

    @Test
    void builderAndAccessors() {
        UUID id = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = createdAt.plusHours(1);

        Tenant tenant = Tenant.builder()
                .id(id)
                .name("Acme")
                .phone("123")
                .companyName("Acme Inc")
                .status(TenantStatus.ACTIVE)
                .email("a@a.com")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .AdminId(adminId)
                .build();

        assertThat(tenant.getId()).isEqualTo(id);
        assertThat(tenant.getName()).isEqualTo("Acme");
        assertThat(tenant.getPhone()).isEqualTo("123");
        assertThat(tenant.getCompanyName()).isEqualTo("Acme Inc");
        assertThat(tenant.getStatus()).isEqualTo(TenantStatus.ACTIVE);
        assertThat(tenant.getEmail()).isEqualTo("a@a.com");
        assertThat(tenant.getCreatedAt()).isEqualTo(createdAt);
        assertThat(tenant.getUpdatedAt()).isEqualTo(updatedAt);
        assertThat(tenant.getAdminId()).isEqualTo(adminId);
    }

    @Test
    void setters() {
        Tenant tenant = new Tenant();
        tenant.setName("New");
        tenant.setPhone("123");
        tenant.setCompanyName("New Inc");
        tenant.setStatus(TenantStatus.SUSPENDED);
        tenant.setEmail("n@n.com");

        assertThat(tenant.getName()).isEqualTo("New");
        assertThat(tenant.getPhone()).isEqualTo("123");
        assertThat(tenant.getCompanyName()).isEqualTo("New Inc");
        assertThat(tenant.getStatus()).isEqualTo(TenantStatus.SUSPENDED);
        assertThat(tenant.getEmail()).isEqualTo("n@n.com");
    }

    @Test
    void allArgsConstructor() {
        UUID id = UUID.randomUUID();
        Tenant tenant = new Tenant(id, "A", "1", "C", TenantStatus.ACTIVE, "e@e.com",
                LocalDateTime.now(), LocalDateTime.now(), UUID.randomUUID());
        assertThat(tenant.getId()).isEqualTo(id);
        assertThat(tenant.getName()).isEqualTo("A");
    }
}