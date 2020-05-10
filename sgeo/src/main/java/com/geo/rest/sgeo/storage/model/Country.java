/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.sgeo.storage.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Country {
    @Id
    private String id; // country code

    private String name;

    private long population;
}
