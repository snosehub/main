/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geo.rest.validation.GeoQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@GeoQuery
@Schema(name = "Query", description = "Object to hold city query information including sorting and pagination")
public class Query {
    @Schema(
            name = "Country name",
            description = "Two character country code, cannot be provided with country name together",
            type = "string",
            minLength = 2,
            maxLength = 2,
            nullable = true)
    @Length(min = 2, max = 2)
    private String countryCode;
    @Schema(
            name = "Country code",
            description = "Country name or part of country name with starting/ending % char, "
                    + "cannot be provided with country code together",
            type = "string",
            nullable = true)
    private String countryName;
    @Schema(
            name = "City name",
            title = "City name or part of city name with starting/ending % char",
            type = "string",
            nullable = true
    )
    private String cityName;
    @Valid
    @Schema(nullable = true)
    private Page page;
    @Valid
    @Schema(nullable = true)
    private Sort sorting;
}