/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.sgeo.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class City {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;
    private String name;
    private Country country;
    private String longitude;
    private String latitude;
    private long population;
}
