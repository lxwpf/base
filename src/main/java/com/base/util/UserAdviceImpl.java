package com.base.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
public class UserAdviceImpl implements UserAdvice {
    @Override
    public Long before(Method method) {
        long startTime = new Date().getTime();
        System.out.println(method.getName() + " 方法执行开始时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
        return startTime;
    }

    @Override
    public Long after(Method method) {
        long endTime = new Date().getTime();
        System.out.println(method.getName() + " 方法执行结束时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
        return endTime;
    }
}
