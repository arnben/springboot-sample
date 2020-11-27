package com.alben.samples.forex.services;

import com.alben.samples.forex.clients.ForexClient;
import com.alben.samples.forex.clients.payloads.ForexClientRatesListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForexService {

    private ForexClient forexClient;

    @Autowired
    private ForexService(ForexClient forexClient) {
        this.forexClient = forexClient;
    }

    public Double getCurrentRate(String currencyCode) {
        ForexClientRatesListResponse ratesListResponse = forexClient.getCurrentRatesList();
        return ratesListResponse.getRates().get(currencyCode);
    }
}
