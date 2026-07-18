package com.realtime_monitorig.tenant_managment.dto;

import com.realtime_monitorig.tenant_managment.entity.TenantStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateTenantRequest {

    private String name;

    @Enumerated(EnumType.STRING)
    private TenantStatus status;

    private String companyName;

    @Email
    private String email;

    private String phone;
}