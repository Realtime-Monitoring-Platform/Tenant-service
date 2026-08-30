package com.realtime_monitorig.tenant_managment.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void constructorAndAccessors() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse response = new ErrorResponse(now, "message", "details");

        assertThat(response.getTimestamp()).isEqualTo(now);
        assertThat(response.getMessage()).isEqualTo("message");
        assertThat(response.getDetails()).isEqualTo("details");

        response.setTimestamp(now.plusHours(1));
        response.setMessage("m2");
        response.setDetails("d2");
        assertThat(response.getTimestamp()).isEqualTo(now.plusHours(1));
        assertThat(response.getMessage()).isEqualTo("m2");
        assertThat(response.getDetails()).isEqualTo("d2");
    }
}