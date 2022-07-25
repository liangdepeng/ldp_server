package com.example.ldpserver.controller;

import com.example.ldpserver.model.BaseBean;
import com.example.ldpserver.model.TestUserBean;
import com.example.ldpserver.requestutils.ResultUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @RequestMapping("getDefaultUser")
    public String getDefaultUser() {
        return "DefaultUser";
    }

    @RequestMapping("getTestUser")
    public BaseBean<TestUserBean> getTestUser() {
        TestUserBean userBean = new TestUserBean();
        return ResultUtils.convertSuccessResponse(userBean);
    }

    @RequestMapping("getTestUserError")
    public BaseBean<TestUserBean> getTestUserError() {
        TestUserBean userBean = new TestUserBean();
        return ResultUtils.convertErrorResponse(null,"查询失败");
    }

    /**
     * GET -请求-  query方式的请求方式
     * <a href="http://host:8080/test/update?age=xx"></a>
     */
    @GetMapping("/test/update")
    public BaseBean<TestUserBean> updateAge(@RequestParam int age){
        TestUserBean userBean = new TestUserBean();
        userBean.setUserAge(age);
        return ResultUtils.convertSuccessResponse(userBean);
    }

    /**
     * GET -请求-  restful风格
     * <a href="http://host:8080/test/update/xx"></a>
     */
    @GetMapping("/test/update/{age}")
    public BaseBean<TestUserBean> updateAge2(@PathVariable("age") int age){
        TestUserBean userBean = new TestUserBean();
        userBean.setUserAge(age);
        return ResultUtils.convertSuccessResponse(userBean);
    }

}
