package com.base.mapper;

import com.base.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
@Repository
public interface UserMapper {

    List<User> queryAll();

    User queryUserByOptions(User user);
}
