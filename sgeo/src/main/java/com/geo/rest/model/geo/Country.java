/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.geo;

import lombok.Data;

@Data
public class Country {

    private String code;

    private String name;

    public Country() {
    }

    public Country(com.geo.storage.model.Country country) {
        this.code = country.getId();
        this.name = country.getName();
    }
}
