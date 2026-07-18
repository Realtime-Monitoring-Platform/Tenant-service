package com.realtime_monitorig.tenant_managment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.realtime_monitorig.tenant_managment.entity.TenantStatus;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TenantResponse {

    private UUID id;

    private String name;

    private String companyName;

    private String email;

    private String phone;

    private TenantStatus status;

    private String subscriptionPlan;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
}