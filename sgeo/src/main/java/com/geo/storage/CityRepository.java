/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.storage;

import com.geo.storage.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value = "SELECT u FROM City u "
            + "WHERE "
            + "(:name is null or lower(u.name) LIKE lower(:name)) AND u.country.id = :countryCode")
    Page<City> findCitiesByNameAndCountryCode(Pageable pageable,
                                              @Param("name") String name,
                                              @Param("countryCode") String countryCode);

    @Query(value = "SELECT u FROM City u "
            + "WHERE "
            + "(:name is null OR lower(u.name) LIKE lower(:name)) "
            + "AND (:countryName is null OR lower(u.country.name) LIKE lower(:countryName))")
    Page<City> findCitiesByNameAndByCountryName(Pageable pageable,
                                                @Param("name") String name,
                                                @Param("countryName") String countryName);
}
