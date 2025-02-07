package com.exchangerat.job.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.exchangerat.job.model.ExchangeRate.ExchangeRate;

@SuppressWarnings("rawtypes")
public interface ExchangeRateRepository extends MongoRepository<ExchangeRate, String>{

    ExchangeRate findByDate(String date);

    @Query("{ 'date' : { $gte: ?0, $lte: ?1 } }")
    List<ExchangeRate> findByDateBetween(String startDate, String endDate);

}
