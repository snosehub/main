/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.search;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class Page {
    @PositiveOrZero
    private Integer page = 0;
    @Positive
    private Integer limit;
}
