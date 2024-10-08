/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.search;

import com.geo.storage.model.City;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

@Data
@Schema(name = "Query sorting")
public class Sort {
    @NotNull
    private City.CityField sortBy = City.CityField.ID;

    private Direction sortDirection = Direction.ASC;
}
