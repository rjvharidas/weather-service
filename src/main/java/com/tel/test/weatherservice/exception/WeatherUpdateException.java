package com.tel.test.weatherservice.exception;

public class WeatherUpdateException extends RuntimeException{
    public WeatherUpdateException(String message) { super(message); }

    public WeatherUpdateException(String message, Throwable cause) { super(message, cause);}
}
