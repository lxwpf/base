package com.base.aop;

import com.alibaba.fastjson.JSON;
import com.base.domain.result.Result;
import com.base.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author lixiong
 * @date 2019 10 2019/10/20
 */
@Aspect
@Component
public class UserAop {

    private static final Logger logger = LoggerFactory.getLogger(UserAop.class);

    @Pointcut("execution(public * com.base.service.impl.*.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        // 所在方法全路径名（类名 + 方法名）
        String methodReference = signature.getDeclaringTypeName() + signature.getName();

        logger.info("日志入口：{}，入参：{}", methodReference, JSON.toJSONString(joinPoint.getArgs()));

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        // 记录下请求内容
        logger.info("请求URL : " + req.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + req.getMethod());
        logger.info("IP : " + req.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void exception(JoinPoint joinPoint, Exception e){
        Signature signature = joinPoint.getSignature();
        String reference = signature.getDeclaringTypeName();
        String method = signature.getName();
        logger.error("异常位置：{}, 调用方法名：{}，入参：{}，异常消息：{}",reference, method, JSON.toJSONString(joinPoint.getArgs()), e.getMessage());
    }
}
