package com.realtime_monitorig.tenant_managment.kafka;

import com.realtime_monitorig.tenant_managment.entity.Tenant;
import com.realtime_monitorig.tenant_managment.entity.TenantStatus;
import com.realtime_monitorig.tenant_managment.kafka.event.TenantCreatedEvent;
import com.realtime_monitorig.tenant_managment.kafka.event.TenantDeletedEvent;
import com.realtime_monitorig.tenant_managment.kafka.event.TenantUpdatedEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TenantProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private TenantProducer producer;
    private Tenant tenant;

    @BeforeEach
    void setUp() {
        producer = new TenantProducer(kafkaTemplate);
        tenant = Tenant.builder().id(UUID.randomUUID()).name("Acme").companyName("Acme Inc")
                .email("a@a.com").phone("123").status(TenantStatus.ACTIVE)
                .AdminId(UUID.randomUUID()).build();
    }

    @Test
    void sendTenantCreation_sendsEvent() {
        producer.sendTenantCreation(tenant);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(org.mockito.ArgumentMatchers.eq("tenant-events-v6"),
                org.mockito.ArgumentMatchers.eq(tenant.getId().toString()), captor.capture());

        assertThat(captor.getValue()).isInstanceOf(TenantCreatedEvent.class);
        TenantCreatedEvent event = (TenantCreatedEvent) captor.getValue();
        assertThat(event.id()).isEqualTo(tenant.getId());
        assertThat(event.name()).isEqualTo("Acme");
        assertThat(event.status()).isEqualTo("ACTIVE");
        assertThat(event.even().eventType()).contains("TENANT_CREATED");
    }

    @Test
    void sendTenantCreation_nullStatus_producesNullStatus() {
        tenant.setStatus(null);
        producer.sendTenantCreation(tenant);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(org.mockito.ArgumentMatchers.eq("tenant-events-v6"),
                org.mockito.ArgumentMatchers.eq(tenant.getId().toString()), captor.capture());

        TenantCreatedEvent event = (TenantCreatedEvent) captor.getValue();
        assertThat(event.status()).isNull();
    }

    @Test
    void sendTenantUpdate_sendsEvent() {
        producer.sendTenantUpdate(tenant);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(org.mockito.ArgumentMatchers.eq("tenant-events-v6"),
                org.mockito.ArgumentMatchers.eq(tenant.getId().toString()), captor.capture());

        assertThat(captor.getValue()).isInstanceOf(TenantUpdatedEvent.class);
        TenantUpdatedEvent event = (TenantUpdatedEvent) captor.getValue();
        assertThat(event.id()).isEqualTo(tenant.getId());
        assertThat(event.event().eventType()).contains("TENANT_UPDATED");
    }

    @Test
    void sendTenantDeleted_sendsEvent() {
        producer.sendTenantDeleted(tenant.getId());

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(org.mockito.ArgumentMatchers.eq("tenant-events-v6"),
                org.mockito.ArgumentMatchers.eq(tenant.getId().toString()), captor.capture());

        assertThat(captor.getValue()).isInstanceOf(TenantDeletedEvent.class);
        TenantDeletedEvent event = (TenantDeletedEvent) captor.getValue();
        assertThat(event.id()).isEqualTo(tenant.getId());
        assertThat(event.event().eventType()).contains("TENANT_DELETED");
    }
}