package com.realtime_monitorig.tenant_managment.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaConfigTest {

    @Test
    void producerFactory_createsConfiguredFactory() {
        KafkaConfig config = new KafkaConfig();
        ReflectionTestUtils.setField(config, "bootstrapServers", "localhost:9092");

        Object factory = config.producerFactory();

        assertThat(factory).isInstanceOf(DefaultKafkaProducerFactory.class);
    }

    @Test
    void kafkaTemplate_createsTemplate() {
        KafkaConfig config = new KafkaConfig();
        ReflectionTestUtils.setField(config, "bootstrapServers", "localhost:9092");
        config.producerFactory();

        KafkaTemplate<String, Object> template = config.kafkaTemplate();

        assertThat(template).isNotNull();
    }
}