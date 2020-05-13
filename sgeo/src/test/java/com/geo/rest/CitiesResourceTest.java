/**
 * Copyright (c) 2020, Sergey Petrov
 */

package com.geo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geo.rest.model.search.Page;
import com.geo.rest.model.search.Query;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CitiesResourceTest {

    private static final String URI_SEARCH = "/cities/search";

    private static final String COUNTRY_CODE = "CZ";
    private static final String COUNTRY_NAME = "Cze%";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void testBadGoodRequest() throws Exception {
        Query query = new Query();
        query.setCountryCode(COUNTRY_CODE);
        query.setCountryName(COUNTRY_NAME);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(CitiesResource.URI_SEARCH)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(jsonMapper.writeValueAsString(query))
                ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        query.setCountryName(null);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(CitiesResource.URI_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(query))
        ).andExpect(MockMvcResultMatchers.status().isOk());
        Page page = new Page();
        page.setPage(-1);
        page.setLimit(10);
        query.setPage(page);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(CitiesResource.URI_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(query))
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        page.setPage(0);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(CitiesResource.URI_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(query))
        ).andExpect(MockMvcResultMatchers.status().isOk());
        page.setLimit(0);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(CitiesResource.URI_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(query))
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testQueryCountryByCode() throws Exception {
        Query query = new Query();
        query.setCountryCode(COUNTRY_CODE);
        //query.setPage(new Page());
        //query.getPage().setPage(-5);
        query.setCityName("D%");
        query.setCountryName(COUNTRY_NAME);
        MvcResult mvcResult = mockMvc
                .perform(
                MockMvcRequestBuilders
                        .post("/cities/search?")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(query))
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();
        log.info(mvcResult.toString());
    }

}
