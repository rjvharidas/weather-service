package com.tel.test.weatherservice.config;

import com.tel.test.weatherservice.exception.WeatherUpdateException;
import lombok.SneakyThrows;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.Series;

@Component
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == Series.CLIENT_ERROR
                || httpResponse.getStatusCode().series() == Series.SERVER_ERROR);
    }

    @SneakyThrows
    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().series() == Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == NOT_FOUND) {
                throw new WeatherUpdateException("Requested City Not Found");
            }
        }else{
            throw new Exception(httpResponse.getBody().toString());
        }
    }
}