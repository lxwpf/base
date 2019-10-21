package com.base.controller;

import com.base.domain.entity.User;
import com.base.domain.result.Result;
import com.base.service.UserService;
import com.base.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lixiong
 * @date 2019 10 2019/10/19
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Result<List<User>> queryAll(){
        return userService.queryAll();
    }

    @RequestMapping("/queryByName")
    @ResponseBody
    public Result<List<User>> queryByName(String username) {
        return Result.buildSuccessResult(userService.queryUserByName(username));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result<User> login(User user, HttpServletResponse response){
        Result<User> result = userService.login(user);
        if(result.isSuccess()) {
            String token = JwtHelper.obtainToken(String.valueOf(result.getData().getId()));
            response.addCookie(new Cookie("token", token));
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(8>>2);
    }
}
