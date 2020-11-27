package com.alben.samples.forex.clients;

import com.alben.samples.forex.clients.payloads.ForexClientRatesListResponse;
import com.alben.samples.utils.TestFileUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

class ForexClientTest {

    private WireMockServer wireMockServer;

    private ForexClient forexClient;

    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
        webClient = WebClient.builder().baseUrl(wireMockServer.baseUrl()).build();
        forexClient = new ForexClient(webClient);
    }

    @Test
    public void getAllRatesTest() throws Exception {
        String mockedResponse =
                TestFileUtils.getStringContent("get_rates.json");

        wireMockServer.stubFor(get("/latest?base=USD")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockedResponse)));

        ForexClientRatesListResponse actualResponse = forexClient.getCurrentRatesList();
        assertThat(actualResponse).isNotNull();
    }

}