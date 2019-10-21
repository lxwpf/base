package com.base.domain.result;

import com.base.enumbase.ResultEnum;
import lombok.Data;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
@Data
public class Result<T> {

    private boolean success;

    private String resultCode;

    private String resultMsg;

    private T data;

    public static <T extends Object> Result<T> buildSuccessResult(T data){
        Result<T> result = new Result<>();
        result.setSuccess(Boolean.TRUE);
        result.setData(data);
        result.setResultCode(ResultEnum.REQUEST_SUCCESS.getCode());
        result.setResultMsg(ResultEnum.REQUEST_SUCCESS.getDesc());
        return result;
    }

    public static <T extends Object> Result<T> buildErrorResult(String errorCode, String errorMsg) {
        Result<T> result = new Result<>();
        result.setSuccess(Boolean.FALSE);
        result.setResultCode(errorCode);
        result.setResultMsg(errorMsg);
        return result;
    }
}
