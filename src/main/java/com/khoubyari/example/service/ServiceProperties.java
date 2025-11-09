package com.khoubyari.example.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;

/*
 * Demonstrates how service-specific properties can be injected.
 */
@ConfigurationProperties(prefix = "hotel.service", ignoreUnknownFields = false)
@Component
public class ServiceProperties {

    @NotNull // ensures this config property must be provided
    private String name = "Empty";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}