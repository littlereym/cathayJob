package com.exchangerat.job.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.exchangerat.job.model.ExchangeRate.ExchangeRate;

@SuppressWarnings("rawtypes")
public interface ExchangeRateRepository extends MongoRepository<ExchangeRate, String> {

    ExchangeRate findByDate(String date);

    // 透過日期區間和貨幣查詢匯率資料
   
    List<ExchangeRate> findByDateBetween(String startDate, String endDate);
}
