package com.base.service;

import com.base.domain.entity.User;
import com.base.domain.result.Result;

import java.util.List;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
public interface UserService {

    Result<List<User>> queryAll();

    Result<User> login(User user);

    List<User> queryUserByName(String name);
}
