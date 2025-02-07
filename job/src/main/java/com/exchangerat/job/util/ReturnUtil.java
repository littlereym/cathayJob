package com.exchangerat.job.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.stereotype.Component;

import com.exchangerat.job.error.ResponseErrorCodeEnum;
import com.exchangerat.job.model.ReturnModel;

@Component
public class ReturnUtil  {
    
    /**
     * 返回成功
	 *
	 * @return json格式字符串
	 * @throws java.io.IOException
	 */
	public static ReturnModel getDataReturn(Object data) throws IOException, URISyntaxException {

		ReturnModel returnModel = new ReturnModel();
		returnModel.setData(data);
		return returnModel;
	}
    
	public static ReturnModel getDataReturnError(ResponseErrorCodeEnum dateNotMatch)
			throws IOException, URISyntaxException {
 
		ReturnModel returnModel = new ReturnModel();
		String errorCode = dateNotMatch.getCode();
		returnModel.setStatus("0000");
		// returnModel.setData();
		returnModel.setErrorCode(errorCode);
        returnModel.setErrorMsg(ResponseErrorCodeEnum.of(errorCode).getDesc());
 
		return returnModel;
	}



}
