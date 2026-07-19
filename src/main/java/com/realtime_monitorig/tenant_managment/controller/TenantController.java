package com.realtime_monitorig.tenant_managment.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.realtime_monitorig.tenant_managment.dto.CreateTenantRequest;
import com.realtime_monitorig.tenant_managment.dto.TenantResponse;
import com.realtime_monitorig.tenant_managment.dto.UpdateTenantRequest;
import com.realtime_monitorig.tenant_managment.entity.TenantStatus;
import com.realtime_monitorig.tenant_managment.service.TenantService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantResponse> create(@Valid @RequestBody CreateTenantRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(tenantService.createTeanant(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TenantResponse>> findAll(@PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(tenantService.getAllTenants(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TenantResponse>> search(@RequestParam String company,@PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable) {

        return ResponseEntity.ok(tenantService.getTenantByCompanyName(company, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<TenantResponse>> filter(@PathVariable TenantStatus status,@PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable) {

        return ResponseEntity.ok(tenantService.filterByStatus(status,pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantResponse> update(@PathVariable UUID id,@RequestBody UpdateTenantRequest request) {
        return ResponseEntity.ok(tenantService.updateTenant(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tenantService.deletetenant(id);
        return ResponseEntity.noContent() .build();
    }

}