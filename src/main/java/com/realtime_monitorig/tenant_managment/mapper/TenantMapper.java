package com.realtime_monitorig.tenant_managment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.dto.UpdateTenantRequest;
import com.realtime_monitorig.tenant_managment.entity.Tenant;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public interface TenantMapper {
    
    Tenant toEntity(CreateTenantRequest request);
    
    
    TenantResponse toResponse(Tenant tenant);
    
    void updateEntityFromRequest(UpdateTenantRequest request, @MappingTarget Tenant tenant);
}