/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.sgeo.rest;

import com.geo.rest.sgeo.rest.model.Cities;
import com.geo.rest.sgeo.rest.model.SearchRequest;
import com.geo.rest.sgeo.rest.service.SearchService;
import com.geo.rest.sgeo.storage.CityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchResource {

    private final CityRepository cityRepository;
    private final SearchService searchService;

    public SearchResource(CityRepository cityRepository, SearchService searchService) {
        this.cityRepository = cityRepository;
        this.searchService = searchService;
    }

    @PostMapping(value = "/search")
    ResponseEntity<Cities> findCities(
            @RequestBody SearchRequest searchRequest,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit
    ) {
        return ResponseEntity.ok(new Cities());
    }
}
