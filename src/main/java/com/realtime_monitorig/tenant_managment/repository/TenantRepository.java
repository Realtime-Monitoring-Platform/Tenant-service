package com.realtime_monitorig.tenant_managment.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import com.realtime_monitorig.tenant_managment.entity.Tenant;
import com.realtime_monitorig.tenant_managment.entity.TenantStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;


@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    
    Optional<Tenant> findByName(String name);
    boolean existsByName(String name);

    Page<Tenant> findByStatus(TenantStatus status, Pageable pageable);

    Page<Tenant> findByCompanyNameContainingIgnoreCase(String companyName, Pageable pageable);
}
