package com.exchangerat.job.Scheduled;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.exchangerat.job.model.ExchangeRate.ExchangeRate;
import com.exchangerat.job.repository.ExchangeRateRepository;
import com.exchangerat.job.util.TimeUtil;
import com.exchangerat.job.util.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class DailyForeignExchangeRatesScheduled {

    @Autowired
    private UrlUtil urlUtil;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Value("${exchange.rate.url}")
    private String exchangeRateUrl;

    private Gson gson = new Gson();

    // 初始化時執行 doGetDailyForeignExchangeRates避免啟動時沒有資料
    @PostConstruct
    public void init() throws IOException, ParseException {
        doGetDailyForeignExchangeRates();
    }
    /*
     * 每天下午 6 點執行一次
     */
    @Scheduled(cron = "0 0 18 * * ?")
    public void doGetDailyForeignExchangeRates() throws IOException, ParseException {
        String response = urlUtil.doGet(exchangeRateUrl);

        Type exchangeRateListType = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        List<Map<String, String>> exchangeRates = gson.fromJson(response, exchangeRateListType);

        if (exchangeRates == null || exchangeRates.isEmpty()) {
            System.out.println("解析失敗或沒有資料");
            return;
        }

        // 逐筆處理匯率資料
        for (Map<String, String> rate : exchangeRates) {
            if (rate == null) {
                continue; 
            }

            String dateToSearch = rate.get("Date").trim();
            String formattedDate = TimeUtil.convertToFullFormat(dateToSearch);

            ExchangeRate existingRate = exchangeRateRepository.findByDate(formattedDate);

            //沒有資料就新增
            if (existingRate == null) {
                Map<String, String> filteredRates = new HashMap<>();
                for (Map.Entry<String, String> entry : rate.entrySet()) {
                    if (entry.getKey().endsWith("/NTD")) {
                        String currency = entry.getKey().split("/")[0];
                        filteredRates.put(currency, entry.getValue());
                    }
                }

                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setDate(formattedDate);
                exchangeRate.setRates(filteredRates);

                // 保存資料到 MongoDB
                exchangeRateRepository.save(exchangeRate);

                System.out.println("Inserted new document for Date: " + formattedDate);
                System.out.println("Rates: " + exchangeRate.getRates());
            } else {
                System.out.println("Document with date " + formattedDate + " already exists. Skipping...");
            }
        }
    }
}
