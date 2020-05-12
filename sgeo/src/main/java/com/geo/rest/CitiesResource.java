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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

@RestController
public class CitiesResource {

    private final CitiesService citiesService;

    public CitiesResource(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping(value = "/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<City> city(@PathVariable Long cityId) {
        try {
            return ResponseEntity.ok(citiesService.getCityById(cityId));
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        }
    }

    @GetMapping(value = "/cities",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Cities> getQuery(
            @Valid @RequestParam(required = false) Query query
    ) {
        return ResponseEntity.ok(citiesService.findCities(query));
    }
}
