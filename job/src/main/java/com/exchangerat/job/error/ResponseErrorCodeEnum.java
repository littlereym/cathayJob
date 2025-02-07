package com.exchangerat.job.error;

import java.util.HashMap;
import java.util.Map;

public enum ResponseErrorCodeEnum {
    /** 请求参数错误 E001 */
    DATE_NOT_MATCH("E001", "日期區間不符"),
   
    // 成功
    SUCCESS("0000", "成功");
   
    /** 状态代码 */
    private final String code;
    /** 状态代码含义描述 */
    private final String desc;
   
    private ResponseErrorCodeEnum(String code, String desc) {
      this.code = code;
      this.desc = desc;
    }
   
    public String getCode() {
      return code;
    }
   
    public String getDesc() {
      return desc;
    }
   
    public static ResponseErrorCodeEnum of(String code) {
      ResponseErrorCodeEnum status[] = ResponseErrorCodeEnum.values();
      for (ResponseErrorCodeEnum statu : status) {
        if (statu.code.equals(code)) {
          return statu;
        }
      }
      return null;
    }

    public static Map<String, String> toErrorResponse(ResponseErrorCodeEnum errorCode) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", errorCode.getCode());
        errorResponse.put("message", errorCode.getDesc());
        return errorResponse;
    }
}
