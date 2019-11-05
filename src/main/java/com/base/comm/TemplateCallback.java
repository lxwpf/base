package com.base.comm;

/**
 * @author lixiong
 * @date 2019 11 2019/11/5
 */
public interface TemplateCallback<T> {

    void checkParams();

    T process();
}
