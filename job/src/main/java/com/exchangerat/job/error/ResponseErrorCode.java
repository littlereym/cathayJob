package com.exchangerat.job.error;

public enum ResponseErrorCode {
     // 成功
     SUCCESS("0000"),
 
 
  // 請求參數錯誤
  DATE_NOT_MATCH("E001");
 
  private String code;
 
  private ResponseErrorCode(String code) {
    this.code = code;
  }
 
  public String getCode() {
    return this.code;
  }
}
