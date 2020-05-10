/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest.sgeo.storage;

import com.geo.rest.sgeo.storage.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value = "SELECT u FROM City u WHERE u.name LIKE :name AND u.country.id = :countryCode")
    Page<City> findCitiesByNameAndCountryCode(Pageable pageable,
                                              @Param("name") String name,
                                              @Param("countryCode") String countryCode);

    @Query(value = "SELECT u FROM City u WHERE u.name LIKE :name")
    Page<City> findCitiesByNameA(Pageable pageable,
                                              @Param("name") String name);

    @Query(value = "SELECT u FROM City u WHERE u.country.id = :countryCode")
    Page<City> findCitiesByCountryCode(Pageable pageable,
                                              @Param("countryCode") String countryCode);

    @Query(value = "SELECT u FROM City u WHERE u.country.name LIKE :countryName")
    Page<City> findCitiesByCountryName(Pageable pageable,
                                       @Param("countryName") String countryName);
}
