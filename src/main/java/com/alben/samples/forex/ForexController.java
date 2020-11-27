package com.alben.samples.forex;

import com.alben.samples.forex.payloads.GetForexResponsePayload;
import com.alben.samples.forex.services.ForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForexController {

    private ForexService forexService;

    @Autowired
    public ForexController(ForexService forexService) {
        this.forexService = forexService;
    }

    @GetMapping("/api/forex/{currencyCode}")
    public GetForexResponsePayload getForex(@PathVariable String currencyCode) {
        Double rate = forexService.getCurrentRate(currencyCode);
        return new GetForexResponsePayload(rate);
    }
}
