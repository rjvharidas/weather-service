package com.tel.test.weatherservice.controller;

import com.tel.test.weatherservice.model.ErrorResponse;
import com.tel.test.weatherservice.model.WeatherUpdate;
import com.tel.test.weatherservice.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.headers.Header;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Weather Updates", description = "the API to search for a weather updates ")
@RestController
@Validated
@RequestMapping("weather/update")
public class WeatherServiceController {

    private final WeatherService weatherService;

    public WeatherServiceController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = "Find weather update by city",
            description = "The API to search for a weather in a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Weather Details" ),
            @ApiResponse(responseCode = "400", description = "Invalid Request"),
            @ApiResponse(responseCode = "500", description = "Internal Errors",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping
    public WeatherUpdate weatherUpdate(@NonNull @RequestParam("country") String country,
                                       @NonNull @RequestParam("city") String city) {
        return weatherService.getUpdate(country, city);
    }
}
