package com.alben.samples.forex.services;

import com.alben.samples.forex.clients.ForexClient;
import com.alben.samples.forex.clients.payloads.ForexClientRatesListResponse;
import com.alben.samples.utils.TestFileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ForexServiceTest {

    @InjectMocks
    private ForexService forexService;

    @Mock
    private ForexClient forexClient;

    @Test
    public void givenValid() {
        ForexClientRatesListResponse response = TestFileUtils.getContent("get_rates.json",
                ForexClientRatesListResponse.class);
        Mockito.when(forexClient.getCurrentRatesList())
                .thenReturn(response);
        Double rate = forexService.getCurrentRate("GBP");
        assertThat(rate).isEqualTo(0.7496131203);
    }

}