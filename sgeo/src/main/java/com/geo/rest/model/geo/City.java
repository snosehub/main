/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.geo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class City {

    private long id;
    private String name;
    private Country country;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private long population;

    public City() {
    }

    public City(com.geo.storage.model.City city) {
        this.id = city.getId();
        this.country = new Country(city.getCountry());
        this.name = city.getName();
        this.longitude = city.getLongitude();
        this.latitude = city.getLatitude();
        this.population = city.getPopulation();
    }
}
