package com.alben.samples.forex.clients;

import com.alben.samples.forex.clients.payloads.ForexClientRatesListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ForexClient {

    public static final String URL = "https://api.exchangeratesapi.io/latest?base=USD";

    private WebClient webClient;

    @Autowired
    public ForexClient( @Qualifier("reqBin") WebClient webClient) {
        this.webClient = webClient;
    }

    public ForexClientRatesListResponse getCurrentRatesList() {
       return webClient.get().uri("/latest?base=USD")
               .header("Content-Type", "application/json")
               .retrieve().bodyToMono(ForexClientRatesListResponse.class).block();
    }
}
