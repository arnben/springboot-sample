package com.alben.samples.acceptance;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Ignore
public class ForexAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProperties() throws Exception {
        mockMvc.perform(
                get( "/api/forex/CAD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate", greaterThan(0d)));
    }

}
