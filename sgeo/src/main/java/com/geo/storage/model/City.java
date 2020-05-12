/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.storage.model;

import lombok.Data;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class City {
    @Id
    private long id;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private long population;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_CODE", referencedColumnName = "ID")
    private Country country;
}
