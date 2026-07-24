package com.realtime_monitorig.tenant_managment.imp;

import com.realtime_monitorig.tenant_managment.repository.TenantRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.dto.UpdateTenantRequest;
import com.realtime_monitorig.tenant_managment.entity.Tenant;
import com.realtime_monitorig.tenant_managment.entity.TenantStatus;
import com.realtime_monitorig.tenant_managment.exceptions.TenantNotFoundException;
import com.realtime_monitorig.tenant_managment.kafka.TenantProducer;
import com.realtime_monitorig.tenant_managment.mapper.TenantMapper;
import com.realtime_monitorig.tenant_managment.service.TenantService;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final TenantProducer tenantProducer;
    @Override
    public TenantResponse createTeanant(CreateTenantRequest request) {
        Tenant tenant = tenantMapper.toEntity(request);
        tenant.setStatus(TenantStatus.ACTIVE);
        Tenant savedTenant = tenantRepository.save(tenant);

        TenantResponse response = tenantMapper.toResponse(savedTenant);
        tenantProducer.sendTenantCreation(savedTenant);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public TenantResponse getTenantById(UUID tennantId) {
        Optional<Tenant> tenant = this.tenantRepository.findById(tennantId);
        if (tenant.isEmpty()) {
            throw new TenantNotFoundException("tenant not found with id: " + tennantId);
        }
        TenantResponse response = tenantMapper.toResponse(tenant.get());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantResponse> getAllTenants(Pageable pageable) {
        return this.tenantRepository.findAll(pageable).map(tenantMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantResponse> getTenantByCompanyName(String companyName, Pageable pageable) {

        return this.tenantRepository.findByCompanyNameContainingIgnoreCase(companyName, pageable)
                .map(tenantMapper::toResponse);
    }

    @Override
    public TenantResponse updateTenant(UUID tenantId, UpdateTenantRequest request) {
        Optional<Tenant> tenantOptional = this.tenantRepository.findById(tenantId);
        if (tenantOptional.isEmpty()) {
            throw new TenantNotFoundException("tenant not found with id: " + tenantId);
        }
        Tenant tenant = tenantOptional.get();
        tenantMapper.updateEntityFromRequest(request, tenant);
        Tenant updatedTenant = tenantRepository.save(tenant);
        tenantProducer.sendTenantUpdate(updatedTenant);
        return tenantMapper.toResponse(updatedTenant);
    }

    @Override
    public void deletetenant(UUID id) {
        this.tenantRepository.deleteById(id);
    }

    @Override
    public Page<TenantResponse> filterByStatus(TenantStatus status, Pageable pageable) {
        return this.tenantRepository.findByStatus(status, pageable).map(tenantMapper::toResponse);
    }

}
