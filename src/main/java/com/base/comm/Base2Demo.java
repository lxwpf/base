package com.base.comm;

import com.alibaba.fastjson.JSON;
import com.base.domain.result.Result;
import com.base.enumbase.ResultEnum;
import org.springframework.util.Assert;

/**
 * @author lixiong
 * @date 2019 11 2019/11/5
 */
public class Base2Demo {

    public static Result<String> test(String name){
        return Base2Template.execute(new TemplateCallback<String>() {
            @Override
            public void checkParams() {
                Assert.hasText(name, ResultEnum.ARGUMENT_ERROR.getDesc());
            }

            @Override
            public String process() {
                return "hi " + name;
            }
        });
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(Base2Demo.test("校长")));
    }
}
