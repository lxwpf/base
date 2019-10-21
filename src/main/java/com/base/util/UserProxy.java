package com.base.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
public class UserProxy {

    private Object target;
    private UserAdvice userAdvice;

    public UserProxy(Object target, UserAdvice userAdvice){
        this.target = target;
        this.userAdvice = userAdvice;
    }

    public Object advice(){
        UserInvocationHandler handler = new UserInvocationHandler(target, userAdvice);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
    }

    class UserInvocationHandler implements InvocationHandler {

        private Object target;

        private UserAdvice userAdvice;

        UserInvocationHandler(Object target, UserAdvice userAdvice){
            this.target = target;
            this.userAdvice = userAdvice;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Long startTime = userAdvice.before(method);
            Object invoke = method.invoke(target, args);
            Long endTime = userAdvice.after(method);
            System.out.println(method.getName() + " 方法执行结束用时：" + (endTime - startTime));
            return invoke;
        }
    }
}
