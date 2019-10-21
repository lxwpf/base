package com.base.exception;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
public class BusinessException extends RuntimeException {

    public BusinessException(){}

    public BusinessException(String errorMsg){
        super(errorMsg);
    }

}
