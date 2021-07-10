package com.tel.test.weatherservice.services;

import com.tel.test.weatherservice.config.AppConfig;
import com.tel.test.weatherservice.exception.WeatherUpdateException;
import com.tel.test.weatherservice.model.OpenWeatherMap;
import com.tel.test.weatherservice.model.WeatherUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private final OpenWeatherMap openWeatherMap;
    private final AppConfig appConfig;

    public WeatherServiceImpl(RestTemplate restTemplate, OpenWeatherMap openWeatherMap, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.openWeatherMap = openWeatherMap;
        this.appConfig = appConfig;
    }

    private static WeatherUpdateException apply() {
        return new WeatherUpdateException("Weather data not found");
    }

    @Override
    @Cacheable(value = "weatherUpdate", key = "#city+#country")
    public WeatherUpdate getUpdate(String country, String city) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(openWeatherMap.getBaseUrl())
                .queryParam("q", addDelimeter(country, city))
                .queryParam("appId", openWeatherMap.getAppId());
        log.info("Weather update call for :" + city);
        return getWeatherUpdate(builder.build().toString()).orElseThrow(WeatherServiceImpl::apply);
    }

    @Override
    public WeatherUpdate getUpdateByLatLon(String lat, String lon) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(openWeatherMap.getBaseUrl())
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appId", openWeatherMap.getAppId());
        log.info("Weather update call for lat:" + lat + " lon: " + lon);
        return getWeatherUpdate(builder.build().toString()).orElseThrow(WeatherServiceImpl::apply);
    }

    private Optional<WeatherUpdate> getWeatherUpdate(String url) {
        var weatherUpdate = restTemplate.getForObject(url, WeatherUpdate.class);
        return Optional.ofNullable(weatherUpdate);
    }

    private String addDelimeter(String country, String city) {
        return city + "," + appConfig.getIsoCountryCode(country);
    }
}
