/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.model.geo;

import com.geo.rest.model.base.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        title = "Cities collection",
        description = "Limited in side cities collection with information"
                + " if more cities may be retrieved by the same query(next page)")
public class Cities extends Page<City> {
}
