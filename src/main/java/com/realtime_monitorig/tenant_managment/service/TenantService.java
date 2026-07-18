package com.realtime_monitorig.tenant_managment.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;

public interface TenantService {

    
    TenantResponse createTeanant(CreateTenantRequest request);
    TenantResponse getTenantById(UUID tennantId);
    Page<TenantResponse> getAllTenants(Pageable pageable);
    Page<TenantResponse> getTenantByCompanyName(String companyName, Pageable pageable);
    TenantResponse updateTenant(UUID tenantId, CreateTenantRequest request);
    void deletetenant(UUID id);


}
