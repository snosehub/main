/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class City {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;
    private String name;
    private Country country;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private long population;

    public City() {
    }

    public City(com.geo.storage.model.City city) {
        this.country = new Country(city.getCountry());
        this.name = city.getName();
        this.longitude = city.getLongitude();
        this.latitude = city.getLatitude();
        this.population = city.getPopulation();
    }
}
