package com.exchangerat.job;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exchangerat.job.Scheduled.DailyForeignExchangeRatesScheduled;
import com.exchangerat.job.model.ExchangeRate.ExchangeRate;
import com.exchangerat.job.model.ExchangeRate.ExchangeRateOutPut;
import com.exchangerat.job.repository.ExchangeRateRepository;
import com.exchangerat.job.service.imp.ExchangeRateMongoServiceImp;
import com.exchangerat.job.util.UrlUtil;

@SpringBootTest
class JobApplicationTests {

    @Mock
    private UrlUtil urlUtil;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeRateMongoServiceImp exchangeRateMongoServiceImp;


    @Autowired
    private DailyForeignExchangeRatesScheduled dailyForeignExchangeRatesScheduled;



    @Test
    void testDoGetDailyForeignExchangeRates() throws IOException, ParseException {
   
        String jsonResponse = "[{\"Date\":\"20250102\",\"USD/NTD\":\"32.868\",\"RMB/NTD\":\"4.486909\"}," +
                "{\"Date\":\"20250103\",\"USD/NTD\":\"32.917\",\"RMB/NTD\":\"4.478983\"}]";

        when(urlUtil.doGet(anyString())).thenReturn(jsonResponse);

        // 模擬查詢結果
        when(exchangeRateRepository.findByDate("20250102")).thenReturn(null);
        when(exchangeRateRepository.findByDate("20250103")).thenReturn(null);

        // 執行批次功能
        dailyForeignExchangeRatesScheduled.doGetDailyForeignExchangeRates();

        
    }

     @Test
    void testSelectExchangeRateMongoByDateAndCurrency() {
       {
            // 模擬查詢結果
            ExchangeRate rate1 = new ExchangeRate();
            rate1.setDate("2025-01-02 00:00:00");
            Map<String, String> rates1 = new HashMap<>();
            rates1.put("USD", "32.868");
            rate1.setRates(rates1);
    
            ExchangeRate rate2 = new ExchangeRate();
            rate2.setDate("2025-01-03 00:00:00");
            Map<String, String> rates2 = new HashMap<>();
            rates2.put("USD", "32.917");
            rate2.setRates(rates2);
    
            String startDate = "20250101";
            String endDate = "20250104";
            when(exchangeRateRepository.findByDateBetween(startDate, endDate)).thenReturn(Arrays.asList(rate1, rate2));
    
            // 執行方法
            Map<String, Object> params = new HashMap<>();
            params.put("startDate", "20250101");
            params.put("endDate", "20250104");
            params.put("currency", "USD");

            ExchangeRateOutPut result = exchangeRateMongoServiceImp.selectExchangeRateMongoByDateAndCurrency(params);

            // Use the result
            System.out.println(result);
        
    }
}
}