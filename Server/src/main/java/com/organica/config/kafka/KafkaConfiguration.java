package com.organica.config.kafka;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final KafkaProperties kafkaProperties;
    @Bean
    public KafkaAdmin kafkaAdmin(KafkaProperties kafkaProperties) {
        Map<String, Object>config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("organica-topic", 2, (short) 1);
    };
}
