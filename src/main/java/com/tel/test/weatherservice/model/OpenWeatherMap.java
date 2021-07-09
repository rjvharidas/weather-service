package com.tel.test.weatherservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api.openweathermap")
public class OpenWeatherMap {
    private String appId;
    private String baseUrl;
}
