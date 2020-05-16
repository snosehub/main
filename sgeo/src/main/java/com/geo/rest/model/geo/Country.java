/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.geo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(title = "Country")
public class Country {

    @Length(max = 2, min = 2)
    @Schema(name = "Country code", description = "Two character country code", example = "CZ")
    private String code;

    @Schema(name = "Country name")
    private String name;

    public Country() {
    }

    public Country(com.geo.storage.model.Country country) {
        this.code = country.getId();
        this.name = country.getName();
    }
}
