package com.realtime_monitorig.tenant_managment.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.dto.UpdateTenantRequest;

public interface TenantService {
    TenantResponse createTeanant(CreateTenantRequest request);
    TenantResponse getTenantById(UUID tennantId);
    Page<TenantResponse> getAllTenants(Pageable pageable);
    Page<TenantResponse> getTenantByCompanyName(String companyName, Pageable pageable);
    TenantResponse updateTenant(UUID tenantId, UpdateTenantRequest request);
    void deletetenant(UUID id);


}
