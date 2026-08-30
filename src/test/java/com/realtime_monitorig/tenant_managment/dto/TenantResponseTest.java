package com.realtime_monitorig.tenant_managment.dto;

import com.realtime_monitorig.tenant_managment.entity.TenantStatus;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TenantResponseTest {

    @Test
    void builderAndGetters() {
        UUID id = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        TenantResponse response = TenantResponse.builder()
                .id(id)
                .name("Acme")
                .companyName("Acme Inc")
                .email("a@a.com")
                .phone("123")
                .status(TenantStatus.ACTIVE)
                .subscriptionPlan("basic")
                .createdAt(createdAt)
                .updatedAt(createdAt.plusHours(1))
                .AdminId(adminId)
                .build();

        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getName()).isEqualTo("Acme");
        assertThat(response.getCompanyName()).isEqualTo("Acme Inc");
        assertThat(response.getEmail()).isEqualTo("a@a.com");
        assertThat(response.getPhone()).isEqualTo("123");
        assertThat(response.getStatus()).isEqualTo(TenantStatus.ACTIVE);
        assertThat(response.getSubscriptionPlan()).isEqualTo("basic");
        assertThat(response.getCreatedAt()).isEqualTo(createdAt);
        assertThat(response.getAdminId()).isEqualTo(adminId);
        assertThat(response.toString()).isNotEmpty();
    }
}