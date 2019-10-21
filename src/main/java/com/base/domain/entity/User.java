package com.base.domain.entity;

import lombok.Data;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
@Data
public class User {

    private Integer id;
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

}
