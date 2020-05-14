/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest;

import com.geo.rest.model.geo.Cities;
import com.geo.rest.model.geo.City;
import com.geo.rest.model.search.Query;
import com.geo.rest.service.CitiesService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

@RestController
@RequestMapping("/cities")
public class CitiesResource {

    private final CitiesService citiesService;

    public CitiesResource(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping(value = "/{cityId|", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<City> city(@PathVariable Long cityId) {
        try {
            return ResponseEntity.ok(citiesService.getCityById(cityId));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Cities> query(
            @Valid @RequestBody Query query
    ) {
        return ResponseEntity.ok(citiesService.findCities(query));
    }
}
