package com.exchangerat.job.model.ExchangeRate;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exchange_rates")
public class ExchangeRate {
  
    private String date;
    private Map<String, String> rates;


   

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }
}