package com.alben.samples.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class ReqBinClientConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ReqBinClientConfiguration.class);

    @Bean
    @Qualifier("reqBin")
    @Profile("!test")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api.exchangeratesapi.io")
                .filter(this.logRequest())
                .filter(this.logResponse())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("WebClient request: {} {} {}", clientRequest.method(), clientRequest.url(), clientRequest.body());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("WebClient response status: {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }
}
