package com.realtime_monitorig.tenant_managment.mapper;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.dto.UpdateTenantRequest;
import com.realtime_monitorig.tenant_managment.entity.Tenant;
import com.realtime_monitorig.tenant_managment.entity.TenantStatus;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TenantMapperTest {

    private final TenantMapper mapper = new TenantMapperImpl();

    @Test
    void toEntity_mapsRequestFields() {
        CreateTenantRequest request = new CreateTenantRequest();
        request.setName("Acme");
        request.setCompanyName("Acme Inc");
        request.setEmail("a@a.com");
        request.setPhone("123");
        request.setStatus(TenantStatus.SUSPENDED);

        Tenant tenant = mapper.toEntity(request);

        assertThat(tenant.getName()).isEqualTo("Acme");
        assertThat(tenant.getCompanyName()).isEqualTo("Acme Inc");
        assertThat(tenant.getEmail()).isEqualTo("a@a.com");
        assertThat(tenant.getPhone()).isEqualTo("123");
        assertThat(tenant.getStatus()).isEqualTo(TenantStatus.SUSPENDED);
    }

    @Test
    void toResponse_mapsTenantFields() {
        UUID id = UUID.randomUUID();
        Tenant tenant = Tenant.builder().id(id).name("Acme").companyName("Acme Inc")
                .email("a@a.com").phone("123").status(TenantStatus.ACTIVE).build();

        TenantResponse response = mapper.toResponse(tenant);

        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getName()).isEqualTo("Acme");
        assertThat(response.getCompanyName()).isEqualTo("Acme Inc");
        assertThat(response.getEmail()).isEqualTo("a@a.com");
        assertThat(response.getPhone()).isEqualTo("123");
        assertThat(response.getStatus()).isEqualTo(TenantStatus.ACTIVE);
    }

    @Test
    void updateEntityFromRequest_updatesProvidedValues() {
        UpdateTenantRequest request = new UpdateTenantRequest();
        request.setName("Updated");
        request.setCompanyName("Updated Inc");
        request.setPhone("999");
        request.setEmail("u@u.com");

        Tenant tenant = Tenant.builder().name("Old").companyName("Old Inc").build();

        mapper.updateEntityFromRequest(request, tenant);

        assertThat(tenant.getName()).isEqualTo("Updated");
        assertThat(tenant.getCompanyName()).isEqualTo("Updated Inc");
        assertThat(tenant.getPhone()).isEqualTo("999");
        assertThat(tenant.getEmail()).isEqualTo("u@u.com");
    }

    @Test
    void updateEntityFromRequest_ignoreNullValues() {
        UpdateTenantRequest request = new UpdateTenantRequest();

        Tenant tenant = Tenant.builder().name("Original").phone("12345").build();

        mapper.updateEntityFromRequest(request, tenant);

        assertThat(tenant.getName()).isEqualTo("Original");
        assertThat(tenant.getPhone()).isEqualTo("12345");
    }

    @Test
    void toEntity_nullRequest_returnsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toResponse_nullTenant_returnsNull() {
        assertThat(mapper.toResponse(null)).isNull();
    }

    @Test
    void updateEntityFromRequest_nullRequest_doesNothing() {
        Tenant tenant = Tenant.builder().name("Keep").build();
        mapper.updateEntityFromRequest(null, tenant);
        assertThat(tenant.getName()).isEqualTo("Keep");
    }
}