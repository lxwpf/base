package com.base.util;

import java.lang.reflect.Method;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
public interface UserAdvice {
    Long before(Method method);
    Long after(Method method);
}
