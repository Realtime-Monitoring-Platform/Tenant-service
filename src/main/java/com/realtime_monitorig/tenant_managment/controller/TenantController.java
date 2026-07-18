package com.realtime_monitorig.tenant_managment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realtime_monitorig.tenant_managment.service.TenantService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;
    
 
    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    
}