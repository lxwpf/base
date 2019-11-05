package com.base.comm;

import com.base.domain.result.Result;
import com.base.enumbase.ResultEnum;
import com.base.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 模板基类，统一异常处理，返回结果处理
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
public class BaseTemplate {

    /**
     * 普通方法执行
     * @param execution 函数修饰接口
     * @param <T>   泛型，任意类型
     * @return  result
     */
    public static <T extends Object> Result<T> execute(Execution<T> execution) {
        Result<T> result = new Result<>();
        try{
            // jdk aop增强打印日志处理
            /*UserProxy proxy = new UserProxy(execution, new UserAdviceImpl());
            execution = (Execution<T>) proxy.advice();*/
            result.setData(execution.execute());
            result.setSuccess(Boolean.TRUE);
            result.setResultCode(ResultEnum.REQUEST_SUCCESS.getCode());
            result.setResultMsg(ResultEnum.REQUEST_SUCCESS.getDesc());
        }catch (BusinessException e){
            result.setSuccess(Boolean.FALSE);
            result.setResultCode(String.valueOf(500));
            result.setResultMsg(e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(Boolean.FALSE);
            result.setResultCode(ResultEnum.UNKNOWN_ERROR.getCode());
            result.setResultMsg(ResultEnum.UNKNOWN_ERROR.getDesc());
        }
        return result;
    }

    @Transactional
    public <T extends Object> Result<T> executeTransactional(Execution<T> execution){
        Result<T> result = new Result<>();
        try{
            result.setData(execution.execute());
            result.setSuccess(Boolean.TRUE);
            result.setResultCode(ResultEnum.REQUEST_SUCCESS.getCode());
            result.setResultMsg(ResultEnum.REQUEST_SUCCESS.getDesc());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @FunctionalInterface
    public interface Execution<T> {

        T execute() throws IOException;
    }
}
