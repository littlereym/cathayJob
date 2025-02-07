package com.exchangerat.job.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.exchangerat.job.model.ExchangeRate.ExchangeRate;
import com.exchangerat.job.model.ExchangeRate.ExchangeRateOutPut;

@Service
public interface ExchangeRateMongoService {
    
    public void saveExchangeRateMongo(ExchangeRate exchangeRate);

    public  ExchangeRateOutPut selectExchangeRateMongoByDateAndCurrency(Map<String, Object> jSONObject);

    public Object isValidDateFormat(Map<String, Object> jSONObject);

}
