package com.geo.rest.model.base;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private List<T> items;
    private boolean hasNext;
}
