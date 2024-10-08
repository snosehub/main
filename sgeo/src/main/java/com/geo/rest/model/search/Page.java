/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.search;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(name = "Query page")
public class Page {
    @PositiveOrZero
    @Schema(type = "integer", minimum = "0")
    private Integer page = 0;
    @Positive
    @Schema(type = "integer", minimum = "1")
    private Integer limit;
}
