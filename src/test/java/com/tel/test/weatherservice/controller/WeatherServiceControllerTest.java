package com.tel.test.weatherservice.controller;

import com.tel.test.weatherservice.WeatherServiceApplicationTests;
import com.tel.test.weatherservice.model.WeatherUpdate;
import com.tel.test.weatherservice.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class WeatherServiceControllerTest extends WeatherServiceApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void testWeatherByCity() throws Exception {
        when(weatherService.getUpdate(any(), any())).thenReturn(new WeatherUpdate());
        mockMvc.perform(get("/weather/update/city")
                .param("city", "chennai")
                .param("country", "IN")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testWeatherByLatLon() throws Exception {
        when(weatherService.getUpdate(any(), any())).thenReturn(new WeatherUpdate());
        mockMvc.perform(get("/weather/update/lat-lon")
                .param("lat", "13.0827")
                .param("lon", "80.2707")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}