package com.exchangerat.job.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exchangerat.job.error.ResponseErrorCodeEnum;
import com.exchangerat.job.model.ExchangeRate.ExchangeRate;
import com.exchangerat.job.model.ExchangeRate.ExchangeRateOutPut;
import com.exchangerat.job.repository.ExchangeRateRepository;
import com.exchangerat.job.service.ExchangeRateMongoService;
import com.exchangerat.job.util.TimeUtil;

@Service("ExchangeRateMongoService")
public class ExchangeRateMongoServiceImp implements ExchangeRateMongoService {

    @Autowired
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateMongoServiceImp(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public void saveExchangeRateMongo(ExchangeRate exchangeRate) {
        // 实现保存逻辑
        Map<String, String> filteredRates = new HashMap<>();
        for (Map.Entry<String, String> entry : exchangeRate.getRates().entrySet()) {
            if (entry.getKey().endsWith("/NTD")) {
                String currency = entry.getKey().split("/")[0];
                filteredRates.put(currency, entry.getValue());
            }
        }
        exchangeRate.setRates(filteredRates);

        // 转换日期格式
        try {
            String formattedDate = TimeUtil.convertToFullFormat(exchangeRate.getDate());
            exchangeRate.setDate(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public ExchangeRateOutPut selectExchangeRateMongoByDateAndCurrency(String startDate, String endDate, String currency) {
        try {
            startDate = TimeUtil.convertToFullFormat(startDate);
            endDate = TimeUtil.convertToFullFormat(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<ExchangeRate> list = exchangeRateRepository.findByDateBetween(startDate, endDate);

        ExchangeRate exchangeRateStartDate = exchangeRateRepository.findByDate(startDate);
        ExchangeRate exchangeRateEndDate = exchangeRateRepository.findByDate(endDate);

        // 檢查欄位
        if (exchangeRateStartDate == null || exchangeRateEndDate == null) {
            throw new IllegalArgumentException("Invalid date range: startDate or endDate not found in the database.");
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (ExchangeRate rate : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", rate.getDate());
            map.put(currency.toLowerCase(), rate.getRates().get(currency.toUpperCase()));
            result.add(map);
        }
        ExchangeRateOutPut exchangeRateOutPut = new ExchangeRateOutPut();
        exchangeRateOutPut.setCurrency(result);
        return exchangeRateOutPut;
    }

    @Override
    public List<Map<String, String>> isValidDateFormat(Map<String, Object> jSONObject) {
        List<Map<String, String>> errors = new ArrayList<>();
        String startDate = (String) jSONObject.get("startDate");
        String endDate = (String) jSONObject.get("endDate");

        if (!isValidDateFormatCheck(startDate) || !isValidDateFormatCheck(endDate)) {
            errors.add(ResponseErrorCodeEnum.toErrorResponse(ResponseErrorCodeEnum.DATE_NOT_MATCH));
        }

        return errors;
    }

    private boolean isValidDateFormatCheck(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}