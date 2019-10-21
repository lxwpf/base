package com.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.base.comm.BaseTemplate;
import com.base.domain.entity.User;
import com.base.domain.result.Result;
import com.base.enumbase.ResultEnum;
import com.base.exception.BusinessException;
import com.base.mapper.UserMapper;
import com.base.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public Result<List<User>> queryAll() {
        return BaseTemplate.execute(() -> mapper.queryAll());
    }

    @Override
    public Result<User> login(User user) {
        return BaseTemplate.execute(() -> {
            // 空参校验
            Optional.ofNullable(user).orElseThrow(() -> new BusinessException(ResultEnum.ARGUMENT_ERROR.getDesc()));
            if(user.getUsername().equals("") || user.getUsername() == null
                    || user.getPassword().equals("") || user.getPassword() == null){
                throw new BusinessException("账号或密码不能为空!");
            }
            return Optional.ofNullable(mapper.queryUserByOptions(user)).orElseThrow(() -> new BusinessException("未查询到用户，请检查用户名密码!"));
        });
    }

    public static void main(String[] args) throws IOException {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println(key.getEncoded().toString());
        PropertiesLoaderUtils.loadAllProperties("key.properties").setProperty("key1", key.getFormat());
    }

    /**
     * mock数据
     * @param name
     * @return
     */
    @Override
    public List<User> queryUserByName(String name) {
        Optional.of(name);
        return Stream.of(new User("xiao", "123"), new User("ming", "234")).collect(Collectors.toList());
    }
}
