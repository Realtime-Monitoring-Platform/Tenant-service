package com.realtime_monitorig.tenant_managment.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TenantStatusTest {

    @Test
    void enumValuesExist() {
        assertThat(TenantStatus.values()).containsExactly(
                TenantStatus.ACTIVE, TenantStatus.INACTIVE, TenantStatus.DELETED, TenantStatus.SUSPENDED);
        assertThat(TenantStatus.valueOf("ACTIVE")).isEqualTo(TenantStatus.ACTIVE);
    }
}