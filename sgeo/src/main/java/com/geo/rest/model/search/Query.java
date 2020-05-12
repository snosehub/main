/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.search;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Query {
    @NotBlank
    private String countryCode;
    private String countryName;
    private String cityName;
    private Page page;
    private Sort sorting;
}