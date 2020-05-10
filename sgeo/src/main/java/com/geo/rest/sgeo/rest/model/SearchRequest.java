/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.sgeo.rest.model;

import lombok.Data;

@Data
public class SearchRequest {
    private String countryCode;
    private String countryName;
    private String cityName;
}
