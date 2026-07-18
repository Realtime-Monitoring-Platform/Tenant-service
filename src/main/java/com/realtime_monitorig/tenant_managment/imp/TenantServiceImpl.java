package com.realtime_monitorig.tenant_managment.imp;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.service.TenantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {@Override
    public TenantResponse createTeanant(CreateTenantRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTeanant'");
    }

    @Override
    public TenantResponse getTenantById(UUID tennantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTenantById'");
    }

    @Override
    public Page<TenantResponse> getAllTenants(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTenants'");
    }

    @Override
    public Page<TenantResponse> getTenantByCompanyName(String companyName, int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTenantByCompanyName'");
    }

    @Override
    public TenantResponse updateTenant(UUID tenantId, CreateTenantRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTenant'");
    }

    @Override
    public void deletetenant(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletetenant'");
    }
    
}
