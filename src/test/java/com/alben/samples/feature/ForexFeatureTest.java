package com.alben.samples.feature;

import com.alben.samples.utils.TestFileUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import static com.alben.samples.feature.ForexFeatureTest.ForexTestConfiguration;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ForexTestConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ForexFeatureTest {

    @TestConfiguration
    @Profile("test")
    static class ForexTestConfiguration {
        @Bean
        @Qualifier("reqBin")
        public WebClient wc(WireMockServer wireMockServer) {
            return  WebClient.builder().baseUrl(wireMockServer.baseUrl()).build();
        }

        @Bean
        public WireMockServer wireMockServer() {
            WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
            wireMockServer.start();
            return wireMockServer;
        }

    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebClient webClient;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        String mockedResponse =
                TestFileUtils.getStringContent("get_rates.json");

        wireMockServer.stubFor(WireMock.get("/latest?base=USD")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockedResponse)));
    }

    @Test
    @DisplayName("Given CAD as currency, it should return rate for CAD with HTTP 200 Status")
    public void getRateForCAD() throws Exception {
        mockMvc.perform(
                get( "/api/forex/CAD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(1.3017661901));
    }

    @Test
    @DisplayName("Given INR as currency, it should return rate for INR with HTTP 200 Status")
    public void getRateForINR() throws Exception {
        mockMvc.perform(
                get( "/api/forex/INR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(73.8889823381));
    }
}
