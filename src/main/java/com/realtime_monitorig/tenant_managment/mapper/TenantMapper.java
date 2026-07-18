package com.realtime_monitorig.tenant_managment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.entity.Tenant;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING
)
public interface TenantMapper {
    Tenant toEntity(CreateTenantRequest tenant);
    TenantResponse toResponse(Tenant tenant);
    
}