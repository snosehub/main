/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.search;

import com.geo.storage.model.City;
import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.NotNull;

@Data
public class Sort {
    @NotNull
    private City.CityField sortBy = City.CityField.ID;

    private Direction sortDirection = Direction.ASC;
}
