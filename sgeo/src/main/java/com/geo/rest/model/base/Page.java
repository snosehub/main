package com.geo.rest.model.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(hidden = true)
public class Page<T> {
    private List<T> items;
    private boolean hasNext;
}
