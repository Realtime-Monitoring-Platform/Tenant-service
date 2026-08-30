package com.realtime_monitorig.tenant_managment.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TenantNotFoundExceptionTest {

    @Test
    void constructor_storesMessage() {
        TenantNotFoundException exception = new TenantNotFoundException("boom");
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo("boom");
    }
}