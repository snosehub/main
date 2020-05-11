/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.sgeo;

import com.geo.rest.sgeo.storage.CityRepository;
import com.geo.rest.sgeo.storage.model.City;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import javax.inject.Inject;

@SpringBootTest
@Slf4j
class SgeoApplicationTests {

    private static final long TEST_POPULATION = 10_000_000;
    private static final String TEST_COUNTRY_CODE = "RU";
    private static final String TEST_COUNTRY = "Russia";
    private static final String TEST_CITY = "Moscow";

    @Inject
    CityRepository repository;

    /**
     * Sanity check for proper db initialization
     */
    @Test
    void contextLoads() {
        Page<City> cities = repository.findCitiesByNameAndCountryCode(Pageable.unpaged(),
                TEST_CITY, TEST_COUNTRY_CODE);
        Assert.isTrue(cities.getTotalElements() == 1, "Number of " + TEST_CITY
                + " in Ru should be 1 but is " + cities.getTotalElements());
        City city = cities.iterator().next();
        log.info(TEST_CITY + ": " + city.toString());
        Assert.isTrue((city.getPopulation() < TEST_POPULATION || !TEST_COUNTRY.equals(city.getCountry().getName())),
                "Something is wrong, population should be >= "
                        + TEST_POPULATION + " and country should be " + TEST_COUNTRY + " but: "
                        + city.getCountry().getName());
    }
}
