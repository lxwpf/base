package com.base.comm;

import com.base.domain.result.Result;
import com.base.enumbase.ResultEnum;
import com.base.exception.BusinessException;

/**
 * 集中处理异常和返回结果
 * @author lixiong
 * @date 2019 11 2019/11/5
 */
public class Base2Template<T> {

    public static <T> Result<T> execute(TemplateCallback<T> callback){
        Result<T> result = null;
        try {
            // 验证参数
            callback.checkParams();
            // 执行方法
            T data = callback.process();
            result = Result.buildSuccessResult(data);
        }catch (BusinessException e){
            // todo do something
            e.printStackTrace();
            result = Result.buildErrorResult(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getDesc());
        }
        return result;
    }
}
