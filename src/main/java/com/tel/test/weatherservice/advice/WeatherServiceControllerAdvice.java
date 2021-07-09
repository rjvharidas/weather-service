package com.tel.test.weatherservice.advice;

import com.tel.test.weatherservice.controller.WeatherServiceController;
import com.tel.test.weatherservice.exception.WeatherUpdateException;
import com.tel.test.weatherservice.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice(assignableTypes = WeatherServiceController.class)
public class WeatherServiceControllerAdvice {

    @ExceptionHandler(WeatherUpdateException.class)
    @ResponseStatus(BAD_REQUEST)
    public final ErrorResponse handleWeatherUpdateException(final WeatherUpdateException e) {
        final String message = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());
        log.error(message, e);
        return getErrorResponse(message, BAD_REQUEST);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        String msg = getErrorMap(ex).toString();
        log.error(msg, ex);
        return getErrorResponse(msg, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public final ErrorResponse handleServerExceptions(final Exception e) {
        final String message = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());
        log.error(message, e);
        return getErrorResponse(message, INTERNAL_SERVER_ERROR);
    }

    public static ErrorResponse getErrorResponse(String message, HttpStatus internalServerError) {
        return ErrorResponse.builder()
                .status(internalServerError.toString())
                .timestamp(new Date().toString())
                .message(message)
                .build();
    }

    public static Map<String, String> getErrorMap(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
