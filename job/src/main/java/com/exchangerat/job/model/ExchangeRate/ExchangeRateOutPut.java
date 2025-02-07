package com.exchangerat.job.model.ExchangeRate;

import java.util.List;
import java.util.Map;

public class ExchangeRateOutPut {
    List<Map<String, Object>> currency;

    public List<Map<String, Object>> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Map<String, Object>> currency) {
        this.currency = currency;
    }
}
