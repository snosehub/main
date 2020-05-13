/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geo.rest.validation.GeoQuery;
import lombok.Data;

import javax.validation.Valid;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@GeoQuery
public class Query {
    private String countryCode;
    private String countryName;
    private String cityName;
    @Valid
    private Page page;
    @Valid
    private Sort sorting;
}