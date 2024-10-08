/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.storage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Country {
    @Id
    private String id; // country code

    private String name;

    private long population;
}
