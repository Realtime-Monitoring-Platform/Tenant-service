package com.realtime_monitorig.tenant_managment.dto;

import com.realtime_monitorig.tenant_managment.entity.TenantStatus;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateTenantRequestTest {

    @Test
    void settersAndGetters() {
        UUID adminId = UUID.randomUUID();
        CreateTenantRequest request = new CreateTenantRequest();
        request.setName("Acme");
        request.setCompanyName("Acme Inc");
        request.setStatus(TenantStatus.ACTIVE);
        request.setEmail("a@a.com");
        request.setPhone("123");
        request.setAdminId(adminId);

        assertThat(request.getName()).isEqualTo("Acme");
        assertThat(request.getCompanyName()).isEqualTo("Acme Inc");
        assertThat(request.getStatus()).isEqualTo(TenantStatus.ACTIVE);
        assertThat(request.getEmail()).isEqualTo("a@a.com");
        assertThat(request.getPhone()).isEqualTo("123");
        assertThat(request.getAdminId()).isEqualTo(adminId);
        assertThat(request.toString()).contains("Acme");
    }
}