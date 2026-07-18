package com.realtime_monitorig.tenant_managment.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;

public interface TenantService {

    
    TenantResponse createTeanant(CreateTenantRequest request);
    TenantResponse getTenantById(UUID tennantId);
    Page<TenantResponse> getAllTenants(int page,int size);
    Page<TenantResponse> getTenantByCompanyName(String companyName,int page,int size);
    TenantResponse updateTenant(UUID tenantId, CreateTenantRequest request);
    void deletetenant(UUID id);

    
}
