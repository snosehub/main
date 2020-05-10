/**
 * Copyright (c) 2020, Sergey Petrov
 *
 * Licensed under the GNU General Public License v3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geo.rest.sgeo;

import com.geo.rest.sgeo.storage.CityRepository;
import com.geo.rest.sgeo.storage.model.City;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;

@SpringBootTest
@Slf4j
class SgeoApplicationTests {

    private static final long TEST_POPULATION = 10_000_000;
    private static final String TEST_COUNTRY_CODE = "RU";
    private static final String TEST_COUNTRY = "Russia";
    private static final String TEST_CITY = "Moscow";

    @Inject
    CityRepository repository;

    /**
     * Sanity check for proper db initialization
     */
    @Test
    void contextLoads() {
        Page<City> cities = repository.findCitiesByNameAndCountryCode(Pageable.unpaged(),
                TEST_CITY, TEST_COUNTRY_CODE);

        if (cities.getTotalElements() == 1) {
            City city = cities.iterator().next();
            log.info(TEST_CITY + ": " + city.toString());
            if (city.getPopulation() < TEST_POPULATION || !TEST_COUNTRY.equals(city.getCountry().getName())) {
                throw new RuntimeException(
                        "Something is wrong, population should be >= "
                                + TEST_POPULATION + " and country should be " + TEST_COUNTRY + " but: "
                                + city.getCountry().getName());
            }
        } else {
            throw new RuntimeException("Number of " + TEST_CITY
                    + " in Ru should be 1 but is " + cities.getTotalElements());
        }
    }

}
