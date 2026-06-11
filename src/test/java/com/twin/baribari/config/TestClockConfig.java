package com.twin.baribari.config;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestClockConfig {

    public static final Instant NOW = Instant.parse("2026-06-11T00:00:00Z");
    public static final ZoneId ZONE = ZoneId.of("Asia/Seoul");
    public static final Clock FIXED_CLOCK = Clock.fixed(NOW, ZONE);

    @Bean
    public Clock clock() {
        return FIXED_CLOCK;
    }
}
