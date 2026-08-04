package com.realtime_monitorig.tenant_managment.dto;

import java.util.UUID;

import com.realtime_monitorig.tenant_managment.entity.TenantStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class CreateTenantRequest {
    @NotBlank(message = "tenant name is required")
    private String name;


    @NotBlank(message = "company name is required")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TenantStatus status;

    @Email
    private String email;


    private String phone;

    private UUID AdminId;


}
