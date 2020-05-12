/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.service;

import com.geo.rest.model.geo.Cities;
import com.geo.rest.model.geo.City;
import com.geo.rest.model.search.Query;
import com.geo.storage.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CitiesService {

    private final CityRepository cityRepository;

    public CitiesService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getCityById(Long id) {
        return new City(cityRepository.findById(id).get());
    }

    public Cities findCities(Query query) {
        Cities ret = new Cities();
        Page<com.geo.storage.model.City> citiesPage;
        if (query != null) {
            Sort sort = query.getSorting() == null
                    ? Sort.unsorted() : Sort.by(query.getSorting().getSortDirection(), query.getSorting().getSortBy());
            Pageable pageable = query.getPage() == null
                    ? Pageable.unpaged() : PageRequest.of(query.getPage().getPage(), query.getPage().getLimit(), sort);
            if (query.getCityName() == null) {
                if (query.getCountryCode() != null) {
                    citiesPage = cityRepository.findCitiesByCountryCode(pageable, query.getCountryCode());
                } else {
                    citiesPage = cityRepository.findCitiesByCountryName(pageable, query.getCountryName());
                }
            } else {
                if (query.getCountryCode() == null && query.getCountryName() == null) {
                    citiesPage = cityRepository.findCitiesByName(pageable, query.getCityName());
                } else if (query.getCountryCode() != null) {
                    citiesPage = cityRepository
                            .findCitiesByNameAndCountryCode(pageable, query.getCityName(), query.getCountryCode());
                } else {
                    citiesPage = cityRepository
                            .findCitiesByNameAndByCountryName(pageable, query.getCityName(), query.getCountryName());
                }
            }
        } else {
            citiesPage = cityRepository.findAll(PageRequest.of(0, 10));
        }
        ret.setHasNext(citiesPage.hasNext());
        ret.setItems(citiesPage.get().map(City::new).collect(Collectors.toList()));
        return ret;
    }
}
