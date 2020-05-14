/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geo.rest.model.geo.Cities;
import com.geo.rest.model.search.Page;
import com.geo.rest.model.search.Query;
import com.geo.rest.model.search.Sort;
import com.geo.rest.service.CitiesService;
import com.geo.storage.model.City;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CitiesResourceTest {

    private static final String URI_SEARCH = "/cities/search";

    private static final String COUNTRY_CODE = "CZ";
    private static final String COUNTRY_NAME = "Cze%";
    private static final String CITY_NAME = "prag%";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testBadGoodRequest() throws Exception {
        Query query = new Query();
        query.setCountryCode(COUNTRY_CODE);
        query.setCountryName(COUNTRY_NAME);
        performQuery(query).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        query.setCountryName(null);
        performQuery(query).andExpect(MockMvcResultMatchers.status().isOk());
        Page page = new Page();
        page.setPage(-1);
        page.setLimit(10);
        query.setPage(page);
        performQuery(query).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        page.setPage(0);
        performQuery(query).andExpect(MockMvcResultMatchers.status().isOk());
        page.setLimit(0);
        performQuery(query).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testQueryByCountryCode() throws Exception {
        Query query = new Query();
        query.setCountryCode(COUNTRY_CODE);
        MvcResult mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Cities foundCities = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.noNullElements(foundCities.getItems(), "There are null elements in response");
        Assert.isTrue(foundCities.getItems().size() == CitiesService.DEFAULT_SIZE,
                CitiesService.DEFAULT_SIZE + " is not equal to " + foundCities.getItems().size());
        Assert.isTrue(foundCities.isHasNext(), "There  should be more pages");
        Assert.isTrue(
                foundCities.getItems().stream().allMatch(city -> COUNTRY_CODE.equals(city.getCountry().getCode())),
                "There are non " + COUNTRY_CODE + " items");
        int custom_size = 100;
        Page page = new Page();
        page.setPage(0);
        page.setLimit(custom_size);
        query.setPage(page);
        mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        com.geo.rest.model.geo.City firstCity = foundCities.getItems().get(0);
        foundCities = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.noNullElements(foundCities.getItems(), "There are null elements in response");
        Assert.isTrue(foundCities.getItems().size() == custom_size,
                custom_size + " is not equal to " + foundCities.getItems().size());
        Assert.isTrue(foundCities.isHasNext(), "There  should be more pages");
        Assert.isTrue(
                foundCities.getItems().stream().allMatch(city -> COUNTRY_CODE.equals(city.getCountry().getCode())),
                "There are non " + COUNTRY_CODE + " items");
        Assert.isTrue(firstCity.equals(foundCities.getItems().get(0)), "First city should be the same");
        Sort sort = new Sort();
        sort.setSortBy(City.CityField.POPULATION);
        query.setSorting(sort);
        mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Cities foundCities2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.noNullElements(foundCities2.getItems(), "There are null elements in response");
        Assert.isTrue(foundCities2.getItems().size() == custom_size,
                custom_size + " is not equal to " + foundCities2.getItems().size());
        Assert.isTrue(foundCities2.isHasNext(), "There  should be more pages");
        Assert.isTrue(
                foundCities2.getItems().stream().allMatch(city -> COUNTRY_CODE.equals(city.getCountry().getCode())),
                "There are non " + COUNTRY_CODE + " items");
        Assert.isTrue(!foundCities.getItems().get(0).equals(foundCities2.getItems().get(0)),
                "Suspicious similar first element with sorting and without");
        //check it's ordered by population
        Assert.isTrue(isSortedAsc(foundCities2.getItems(), com.geo.rest.model.geo.City::getPopulation),
                "Cities aren't ordered by population");
    }

    @Test
    public void testQueryByName() throws Exception {
        Query query = new Query();
        query.setCityName(CITY_NAME);
        MvcResult mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Cities foundCities = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.noNullElements(foundCities.getItems(), "There are null elements in response");
        Assert.isTrue(foundCities.getItems().size() == CitiesService.DEFAULT_SIZE,
                CitiesService.DEFAULT_SIZE + " is not equal to " + foundCities.getItems().size());
        Assert.isTrue(foundCities.isHasNext(), "There should be more pages");
        Assert.isTrue(
                foundCities.getItems().stream().anyMatch(city -> COUNTRY_CODE.equals(city.getCountry().getCode())),
                "Can't find " + COUNTRY_CODE + " items");
        int custom_size = 100;
        Page page = new Page();
        page.setPage(0);
        page.setLimit(custom_size);
        query.setPage(page);
        mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        com.geo.rest.model.geo.City firstCity = foundCities.getItems().get(0);
        foundCities = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.noNullElements(foundCities.getItems(), "There are null elements in response");
        Assert.isTrue(foundCities.getItems().size() < custom_size,
                custom_size + " should be bigger than number of cities: " + foundCities.getItems().size());
        Assert.isTrue(!foundCities.isHasNext(), "There shouldn't be more pages");
        Assert.isTrue(firstCity.equals(foundCities.getItems().get(0)), "First city should be the same");
        Sort sort = new Sort();
        sort.setSortBy(City.CityField.COUNTRY_NAME);
        query.setSorting(sort);
        mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Cities foundCities2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.noNullElements(foundCities2.getItems(), "There are null elements in response");
        Assert.isTrue(foundCities2.getItems().size() == foundCities.getItems().size(),
                foundCities.getItems().size() + " is not equal to " + foundCities2.getItems().size()
                        + " (different count for different ordering");
        Assert.isTrue(!foundCities2.isHasNext(), "There shouldn't be more pages");
        //check it's ordered by country name
        Assert.isTrue(isSortedAsc(foundCities2.getItems(), c->c.getCountry().getName()),
                "Cities aren't ordered by country nae");
    }

    @Test
    public void testQueryByNameCountryName() throws Exception {
        Query query = new Query();
        query.setCityName(CITY_NAME);
        query.setCountryName(COUNTRY_NAME);
        MvcResult mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Cities foundCities = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.noNullElements(foundCities.getItems(), "There are null elements in response");
        Assert.isTrue(foundCities.getItems().size() == 1,
                "Found extra city");
        Assert.isTrue(!foundCities.isHasNext(), "Shouldn't get more pages");
        Assert.isTrue(
                foundCities.getItems().stream().anyMatch(city -> COUNTRY_CODE.equals(city.getCountry().getCode())),
                "Can't find " + COUNTRY_CODE + " items");
        int custom_size = 100;
        Page page = new Page();
        page.setPage(0);
        page.setLimit(custom_size);
        query.setPage(page);
        mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        com.geo.rest.model.geo.City firstCity = foundCities.getItems().get(0);
        Assert.isTrue("Prague".equals(firstCity.getName()), "Got" + firstCity.getName()
                + " instead of Prague");
        foundCities = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.noNullElements(foundCities.getItems(), "There are null elements in response");
        Assert.isTrue(foundCities.getItems().size() < custom_size,
                custom_size + " should be bigger than number of cities: " + foundCities.getItems().size());
        Assert.isTrue(!foundCities.isHasNext(), "There shouldn't be more pages");
        Assert.isTrue(firstCity.equals(foundCities.getItems().get(0)), "First city should be the same");
        page.setPage(10);
        mvcResult = performQuery(query).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        foundCities = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cities.class);
        Assert.isTrue(foundCities.getItems().size() == 0,
                "Found extra city");
    }

    private ResultActions performQuery(Query query) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .post(URI_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(query))
        );
    }

    private boolean isSortedAsc(List<com.geo.rest.model.geo.City> cities,
                                Function<com.geo.rest.model.geo.City, Comparable> mapper) {
        Comparable prev = null;
        for (com.geo.rest.model.geo.City city: cities) {
            Comparable el = mapper.apply(city);
            if (prev != null) {
                if (el.compareTo(prev) == -1) {
                    return false;
                }
            }
            prev = el;
        }
        return true;
    }
}
