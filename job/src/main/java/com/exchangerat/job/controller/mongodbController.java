package com.exchangerat.job.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exchangerat.job.error.ResponseErrorCodeEnum;
import com.exchangerat.job.model.ExchangeRate.ExchangeRateOutPut;
import com.exchangerat.job.service.ExchangeRateMongoService;
import com.exchangerat.job.util.ReturnUtil;

import io.swagger.annotations.Api;

@RestController
@Controller
@Api(tags = "匯率查詢")
public class mongodbController {

    @Autowired
    ExchangeRateMongoService exchangeRateMongoService;

    @PostMapping(value = "/forex")
    public Object getInviteCode(@RequestBody Map<String, Object> jSONObject) throws ParseException, IOException, URISyntaxException {
        List<Map<String, String>> errors = (List<Map<String, String>>) exchangeRateMongoService.isValidDateFormat(jSONObject);

        //格式檢查
        if (!errors.isEmpty()) {
            return ReturnUtil.getDataReturnError(ResponseErrorCodeEnum.DATE_NOT_MATCH);
        }

        try {
            ExchangeRateOutPut result = exchangeRateMongoService.selectExchangeRateMongoByDateAndCurrency(jSONObject);
            return ReturnUtil.getDataReturn(result.getCurrency());
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.getDataReturnError(ResponseErrorCodeEnum.DATE_NOT_MATCH);
        }
    }

    

}
