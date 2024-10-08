/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest;

import com.geo.rest.model.geo.Cities;
import com.geo.rest.model.geo.City;
import com.geo.rest.model.search.Query;
import com.geo.rest.service.CitiesService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cities")
@OpenAPIDefinition(
        info =
        @Info(
                title = "Cities API",
                version = "1.0",
                description = "Alternative API for geo names cities from geonames.org"
        )
)
public class CitiesResource {

    private final CitiesService citiesService;

    public CitiesResource(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping(value = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get city by geo id",
            method = "GET",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The city",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = City.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "City not found",
                            content = @Content(schema = @Schema)
                    )
            })
    ResponseEntity<City> city(
            @Parameter(name = "City id", example = "13", schema = @Schema(type = "integer"))
            @PathVariable Long cityId) {
        try {
            return ResponseEntity.ok(citiesService.getCityById(cityId));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(method = "POST",
            summary = "Query API for cities by country code/name/city name, "
                    + "requesting specific result page and with provided sorting",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cities",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Cities.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad query format",
                            content = @Content(schema = @Schema)
                    )
            }
    )
    @PostMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Cities> query(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = Query.class)))
                    Query query
    ) {
        return ResponseEntity.ok(citiesService.findCities(query));
    }
}
