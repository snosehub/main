package com.geo.rest;

import com.geo.rest.model.search.Query;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.inject.Inject;

@SpringBootTest
@AutoConfigureMockMvc
public class CitiesResourceTest {

    private static final String COUNTRY_CODE = "CZ";

    @Inject
    private MockMvc mockMvc;

    @Test
    public void testQueryCountryByCode() {
        Query query = new Query();
        query.setCountryCode(COUNTRY_CODE);

        MockMvcRequestBuilders.get("/cities?query={query}", query);
    }

}
