package com.realtime_monitorig.tenant_managment.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenTest {

    @Test
    void canInstantiate() {
        Token token = new Token();
        assertThat(token).isNotNull();
    }
}