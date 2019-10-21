package com.base.enumbase;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
public enum ResultEnum {

    ARGUMENT_ERROR("ARGUMENT_ERROR", "参数异常")
    ,
    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误")
    ,

    // 成功
    REQUEST_SUCCESS("REQUEST_SUCCESS", "请求成功")
    ;

    private String code;
    private String desc;

    private ResultEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
