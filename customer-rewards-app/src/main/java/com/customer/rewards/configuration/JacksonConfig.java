package com.customer.rewards.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing Jackson's ObjectMapper.
 * Ensures proper handling of Java 8 date/time types.
 */
@Configuration
public class JacksonConfig {

    /**
     * Provides a customized {@link ObjectMapper} bean that:
     * <ul>
     *   <li>Registers the {@link JavaTimeModule} for Java 8 time support</li>
     *   <li>Disables timestamp format for dates (uses ISO-8601 instead)</li>
     * </ul>
     *
     * @return configured ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
