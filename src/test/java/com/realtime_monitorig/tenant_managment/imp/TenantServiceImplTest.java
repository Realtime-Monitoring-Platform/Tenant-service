package com.realtime_monitorig.tenant_managment.imp;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.dto.UpdateTenantRequest;
import com.realtime_monitorig.tenant_managment.entity.Tenant;
import com.realtime_monitorig.tenant_managment.entity.TenantStatus;
import com.realtime_monitorig.tenant_managment.exceptions.TenantNotFoundException;
import com.realtime_monitorig.tenant_managment.kafka.TenantProducer;
import com.realtime_monitorig.tenant_managment.mapper.TenantMapper;
import com.realtime_monitorig.tenant_managment.repository.TenantRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TenantServiceImplTest {

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private TenantMapper tenantMapper;

    @Mock
    private TenantProducer tenantProducer;

    @InjectMocks
    private TenantServiceImpl tenantService;

    private UUID id;
    private Tenant tenant;
    private TenantResponse response;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        tenant = Tenant.builder().id(id).name("Acme").companyName("Acme Inc")
                .email("a@a.com").phone("123").status(TenantStatus.ACTIVE)
                .AdminId(UUID.randomUUID()).build();
        response = TenantResponse.builder().id(id).name("Acme").companyName("Acme Inc")
                .email("a@a.com").phone("123").status(TenantStatus.ACTIVE).build();
    }
@Test
    void createTenant_setsStatusToActiveAndPublishesEvent() {
        CreateTenantRequest request = new CreateTenantRequest();
        request.setName("Acme");
        request.setCompanyName("Acme Inc");
        request.setAdminId(UUID.randomUUID());

        when(tenantMapper.toEntity(request)).thenReturn(tenant);
        when(tenantRepository.save(tenant)).thenReturn(tenant);
        when(tenantMapper.toResponse(tenant)).thenReturn(response);

        TenantResponse result = tenantService.createTeanant(request);

        assertThat(result).isEqualTo(response);
        assertThat(tenant.getStatus()).isEqualTo(TenantStatus.ACTIVE);
        verify(tenantProducer).sendTenantCreation(tenant);
    }

    @Test
    void getTenantById_existingTenant_returnsResponse() {
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));
        when(tenantMapper.toResponse(tenant)).thenReturn(response);

        TenantResponse result = tenantService.getTenantById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getTenantById_missing_throws() {
        when(tenantRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tenantService.getTenantById(id))
                .isInstanceOf(TenantNotFoundException.class)
                .hasMessageContaining("tenant not found");
    }

    @Test
    void getAllTenants_returnsMappedPage() {
        Page<Tenant> tenantPage = new PageImpl<>(List.of(tenant));
        when(tenantRepository.findAll(any(Pageable.class))).thenReturn(tenantPage);
        when(tenantMapper.toResponse(tenant)).thenReturn(response);

        Page<TenantResponse> result = tenantService.getAllTenants(PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(response);
    }

    @Test
    void getTenantByCompanyName_returnsMappedPage() {
        Page<Tenant> tenantPage = new PageImpl<>(List.of(tenant));
        when(tenantRepository.findByCompanyNameContainingIgnoreCase("acme", PageRequest.of(0, 10)))
                .thenReturn(tenantPage);
        when(tenantMapper.toResponse(tenant)).thenReturn(response);

        Page<TenantResponse> result = tenantService.getTenantByCompanyName("acme", PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(response);
    }

    @Test
    void updateTenant_existing_returnsUpdated() {
        UpdateTenantRequest request = new UpdateTenantRequest();
        request.setName("NewName");
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));
        when(tenantRepository.save(tenant)).thenReturn(tenant);
        when(tenantMapper.toResponse(tenant)).thenReturn(response);

        TenantResponse result = tenantService.updateTenant(id, request);

        assertThat(result).isEqualTo(response);
        verify(tenantMapper).updateEntityFromRequest(request, tenant);
        verify(tenantProducer).sendTenantUpdate(tenant);
    }

    @Test
    void updateTenant_notFound_throws() {
        UpdateTenantRequest request = new UpdateTenantRequest();
        when(tenantRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tenantService.updateTenant(id, request))
                .isInstanceOf(TenantNotFoundException.class);
    }

    @Test
    void deleteTenant_deletesAndPublishes() {
        tenantService.deletetenant(id);
        verify(tenantRepository).deleteById(id);
        verify(tenantProducer).sendTenantDeleted(id);
    }

    @Test
    void filterByStatus_returnsMappedPage() {
        Page<Tenant> tenantPage = new PageImpl<>(List.of(tenant));
        when(tenantRepository.findByStatus(TenantStatus.ACTIVE, PageRequest.of(0, 10))).thenReturn(tenantPage);
        when(tenantMapper.toResponse(tenant)).thenReturn(response);

        Page<TenantResponse> result = tenantService.filterByStatus(TenantStatus.ACTIVE, PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(response);
    }
}