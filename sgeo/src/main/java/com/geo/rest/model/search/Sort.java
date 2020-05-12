package com.geo.rest.model.search;

import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.NotBlank;

@Data
public class Sort {
    @NotBlank
    private String sortBy;

    private Direction sortDirection = Direction.ASC;
}
