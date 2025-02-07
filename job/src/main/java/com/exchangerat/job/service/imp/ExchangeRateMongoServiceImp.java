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
        Map<String, String> filteredRates = new HashMap<>();
        for (Map.Entry<String, String> entry : exchangeRate.getRates().entrySet()) {
            if (entry.getKey().endsWith("/NTD")) {
                String currency = entry.getKey().split("/")[0];
                filteredRates.put(currency, entry.getValue());
            }
        }
        exchangeRate.setRates(filteredRates);

        
        try {
            String formattedDate = TimeUtil.convertToFullFormat(exchangeRate.getDate());
            exchangeRate.setDate(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public ExchangeRateOutPut selectExchangeRateMongoByDateAndCurrency(Map<String, Object> jSONObject) {
        try {

            String startDate = (String) jSONObject.get("startDate");
            String endDate = (String) jSONObject.get("endDate");
            String currency = (String) jSONObject.get("currency");
            currency = currency.toUpperCase();
            startDate = TimeUtil.convertToFullFormat(startDate);
            endDate = TimeUtil.convertToFullFormat(endDate);

            List<ExchangeRate> list = exchangeRateRepository.findByDateBetween(startDate, endDate);

            List<Map<String, Object>> result = new ArrayList<>();
            // 將查詢結果轉換成指定格式
            for (ExchangeRate rate : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("date", rate.getDate());
                map.put(currency.toLowerCase(), rate.getRates().get(currency.toUpperCase()));
                result.add(map);
            }
            ExchangeRateOutPut exchangeRateOutPut = new ExchangeRateOutPut();
            exchangeRateOutPut.setCurrency(result);
            return exchangeRateOutPut;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
