package com.tel.test.weatherservice.controller;

import com.tel.test.weatherservice.model.ErrorResponse;
import com.tel.test.weatherservice.model.WeatherUpdate;
import com.tel.test.weatherservice.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


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
            @ApiResponse(responseCode = "200", description = "Found Weather Details",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = WeatherUpdate.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Errors",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("city")
    public WeatherUpdate weatherUpdate(
            @Parameter(description = "ISO 3166 country codes")
            @NotBlank @RequestParam("country") String country,
            @Parameter(description = "city name corresponding to the country")
            @NotBlank @RequestParam("city") String city) {
        return weatherService.getUpdate(country, city);
    }

    @Operation(summary = "Find weather update by lat lon coordinates",
            description = "The API to search for a weather based on lat lon coordinates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Weather Details",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = WeatherUpdate.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Errors",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("lat-lon")
    public WeatherUpdate weatherUpdateByLatLon(
            @Parameter(description = "latitude coordinate")
            @NotBlank @RequestParam("lat") String lat,
            @Parameter(description = "longitude coordinate")
            @NotBlank @RequestParam("lon") String lon) {
        return weatherService.getUpdateByLatLon(lat, lon);
    }
}
