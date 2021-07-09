package com.tel.test.weatherservice.config;

import com.tel.test.weatherservice.exception.WeatherUpdateException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Set;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Weather Update API",
                version = "1.0.0",
                description = "The Weather Service Api is a proxy service to provide" +
                        " weather updates based on the location",
                contact = @Contact(
                        name = "Rajeev Haridas",
                        email = "rjv.haridas@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class AppConfig {
    private final Set<String> isoCountryCode = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2);

    private static WeatherUpdateException get() {
        return new WeatherUpdateException("Invalid Country!!");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public String getIsoCountryCode(String country) {
        return isoCountryCode
                .stream()
                .filter(c -> c.contains(country))
                .findFirst()
                .orElseThrow(AppConfig::get);
    }
}
