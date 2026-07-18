package com.realtime_monitorig.tenant_managment.repository;

import org.springframework.stereotype.Repository;
import java.util.UUID;
import com.realtime_monitorig.tenant_managment.entity.Tenant;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public class TenantRepository extends JpaRepository<Tenant, UUID> {
    
}
