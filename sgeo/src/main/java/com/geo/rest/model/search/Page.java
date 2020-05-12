package com.geo.rest.model.search;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class Page {
    @PositiveOrZero
    private Integer page;
    @Positive
    private Integer limit;
}
