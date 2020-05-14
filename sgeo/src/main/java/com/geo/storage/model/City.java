/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.storage.model;

import lombok.Data;

import java.math.BigDecimal;
import javax.persistence.Column;
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

    @Column(precision = 10, scale = 5)
    private BigDecimal longitude;

    @Column(precision = 10, scale = 5)
    private BigDecimal latitude;

    private long population;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_CODE", referencedColumnName = "ID")
    private Country country;

    public enum CityField {
        ID("id"),
        NAME("name"),
        LONGITUDE("longitude"),
        LATITUDE("latitude"),
        POPULATION("population"),
        COUNTRY_CODE("country.id"),
        COUNTRY_NAME("country.name");

        private final String entityField;

        CityField(String entityField) {
            this.entityField = entityField;
        }

        public String getEntityField() {
            return entityField;
        }
    }
}
