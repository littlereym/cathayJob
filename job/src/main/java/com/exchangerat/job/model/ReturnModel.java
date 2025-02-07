package com.exchangerat.job.model;

public class ReturnModel {
 
	/**
	 * 返回狀態, 0:異常; 1:正常
	 */
	private String status = "0000";
 
	/**
	 * 返回數據
	 */
	private Object data;
 
	/**
	 * 錯誤碼
	 * 
	 * @see ResponseErrorCode
	 */
	private String errorCode = "0000";
 
	/**
	 * 訊息內容
	 */
	private String errorMsg;
 
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
 
	public Object getData() {
		return data;
	}
 
	public void setData(Object data) {
		this.data = data;
	}
 
	public String getErrorCode() {
		return errorCode;
	}
 
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
 
	public String getErrorMsg() {
		return errorMsg;
	}
 
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}