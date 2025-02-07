package com.exchangerat.job.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.exchangerat.job.error.ResponseErrorCodeEnum;

@Component
public class ReturnUtil  {
    
    public static Map<String, Object> getDataReturn(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ResponseErrorCodeEnum.toErrorResponse(ResponseErrorCodeEnum.SUCCESS));
        response.put("currency", data);
        return response;
    }

    public static Map<String, Object> getDataReturnError(ResponseErrorCodeEnum errorCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", ResponseErrorCodeEnum.toErrorResponse(errorCode));
        return response;
    }

}
