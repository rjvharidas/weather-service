package com.tel.test.weatherservice.services;

import com.tel.test.weatherservice.model.WeatherUpdate;

public interface WeatherService {

    WeatherUpdate getUpdate(String country, String city);
}
