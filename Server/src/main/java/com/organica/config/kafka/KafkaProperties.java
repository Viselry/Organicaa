package com.organica.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
@ConfigurationProperties(prefix="spring.kafka")
public class KafkaProperties {
    private String bootstrapServers;
}
