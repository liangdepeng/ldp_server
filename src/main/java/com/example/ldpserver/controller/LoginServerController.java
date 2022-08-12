package com.example.ldpserver.controller;

import com.example.ldpserver.cache.UserCacheManager;
import com.example.ldpserver.cache.UserInfo;
import com.example.ldpserver.model.BaseBean;
import com.example.ldpserver.model.User;
import com.example.ldpserver.requestutils.JsonUtil;
import com.example.ldpserver.requestutils.ResultUtils;
import com.example.ldpserver.token.TokenBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class LoginServerController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public BaseBean<UserInfo> loginToServer(@RequestBody UserInfo userInfo) {
        String userId = userInfo.getUserId();
        String userPwd = userInfo.getUserPwd();
        if (!UserCacheManager.getInstance().checkUserExist(userId)) {
            return ResultUtils.convertSuccessResponse(null, "账号不存在！");
        }
        if (UserCacheManager.getInstance().checkUserAndPwd(userId, userPwd)) {
            UserInfo info = UserCacheManager.getInstance().getUserInfo(userId);
            return ResultUtils.convertSuccessResponse(info, "登录成功");
        } else {
            return ResultUtils.convertSuccessResponse(null, "用户名或密码不正确");
        }
    }

    @PostMapping(value = "/register" ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseBean<Boolean> registerToServer(@RequestBody UserInfo userInfo) {
        String userId = userInfo.getUserId();
        //  String userPwd = userInfo.getUserPwd();
        if (UserCacheManager.getInstance().checkUserExist(userId)) {
            return ResultUtils.convertSuccessResponse(false, "账号已存在！");
        }

        long num = (long) (Math.random() * 123456789 + 654321);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String str = "09r7aUIBBQ"+num+timestamp;
        String registerTokenUrl = "https://api-cn.ronghub.com/user/getToken.json";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.add("App-Key", "p5tvi9dspkvr4"); //09r7aUIBBQ
        httpHeaders.add("Nonce", String.valueOf(num));
        httpHeaders.add("Timestamp", timestamp);
        httpHeaders.add("Signature", getStrSha1(str));

      //  User user = new User(userInfo.getUserId(),userInfo.getUserName(),"_");


        // form 提交表单
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("userId",userInfo.getUserId());
        params.add("name",userInfo.getUserName());
        params.add("portraitUri","_");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(registerTokenUrl, httpEntity, String.class);

        if (HttpStatus.OK == response.getStatusCode()) {
            String body = response.getBody();
            TokenBean bean = JsonUtil.toDataBean(body, TokenBean.class);
            if (bean != null) {
                userInfo.setToken(bean.getToken());
            }

            UserCacheManager.getInstance().addNewUser(userInfo);
            return ResultUtils.convertSuccessResponse(true, "注册成功！");
        }
        return ResultUtils.convertErrorResponse(false, "请求异常！");
    }

    private String getStrSha1(String word) {
        String sh1Str = ":";
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA-1");
            byte[] digest = mDigest.digest(word.getBytes(StandardCharsets.UTF_8));
            BigInteger b = new BigInteger(1, digest);
            sh1Str = b.toString(16);
            while (sh1Str.length() < 32) {
                sh1Str = "0" + sh1Str;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return sh1Str;
    }
}
