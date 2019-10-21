package com.base.interceptor;

import com.base.domain.result.Result;
import com.base.exception.BusinessException;
import com.base.util.JwtHelper;
import io.jsonwebtoken.Claims;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * @author lixiong
 * @date 2019 10 2019/10/21
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    /**
     * 登录拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Result<String> result = new Result<>();
        String tokenId = request.getParameter("tokenId");
        if(StringUtils.isEmpty(tokenId)){
            Logger.warn("token过期，或非法请求！");
            response.sendRedirect("/toLogin");
            return false;
        }
        try {
            Claims claims = JwtHelper.verifyJwt(tokenId);
            Logger.info("id = " + claims.getId());
            Logger.info("userId = " + claims.get("userId"));
            Logger.info("创建时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
            Logger.info("有效时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        }catch (BusinessException e){
            UUID uuid = UUID.randomUUID();
            result.setResultCode("500");
            result.setResultMsg("token校验异常!,traceId = " + uuid);
            Logger.info("traceId: {}，token校验异常：{}", uuid, e);
        }catch (Exception e){
            UUID uuid = UUID.randomUUID();
            result.setResultCode("500");
            result.setResultMsg("token校验异常!,traceId = " + uuid);
            Logger.info("traceId: {}，token校验异常：{}", uuid, e);
        }
        return true;
    }
}
