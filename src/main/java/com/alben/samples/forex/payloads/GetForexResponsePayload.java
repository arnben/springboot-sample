package com.alben.samples.forex.payloads;

public class GetForexResponsePayload {
    private Double rate;

    public GetForexResponsePayload(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }
}
