/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.sgeo.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Cities {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer size;

    private List<City> items = Collections.emptyList();

}
