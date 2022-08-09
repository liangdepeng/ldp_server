package com.example.ldpserver.controller;

import com.alibaba.fastjson2.JSON;
import com.example.ldpserver.cache.UserCacheManager;
import com.example.ldpserver.cache.UserInfo;
import com.example.ldpserver.model.BaseBean;
import com.example.ldpserver.model.TestUserBean;
import com.example.ldpserver.model.VideoListBean;
import com.example.ldpserver.requestutils.HttpUrl;
import com.example.ldpserver.requestutils.JsonUtil;
import com.example.ldpserver.requestutils.ResultUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
        return ResultUtils.convertErrorResponse(null, "查询失败");
    }

    /**
     * GET -请求-  query方式的请求方式
     * <a href="http://host:8080/test/update?age=xx"></a>
     */
    @GetMapping("/test/update")
    public BaseBean<TestUserBean> updateAge(@RequestParam int age) {
        TestUserBean userBean = new TestUserBean();
        userBean.setUserAge(age);
        return ResultUtils.convertSuccessResponse(userBean);
    }

    /**
     * GET -请求-  restful风格
     * <a href="http://host:8080/test/update/xx"></a>
     */
    @GetMapping("/test/update/{age}")
    public BaseBean<TestUserBean> updateAge2(@PathVariable("age") int age) {
        TestUserBean userBean = new TestUserBean();
        userBean.setUserAge(age);
        return ResultUtils.convertSuccessResponse(userBean);
    }


    @GetMapping("/test/getMovies")
    public BaseBean<VideoListBean.DataDTO> getMovies() {
        HttpURLConnection connection;
        Gson gson = new Gson();
        try {
            URL url = new URL("https://front-gateway.mtime.cn/ticket/schedule/top/movies.api?locationId=290");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            //  connection.addRequestProperty("Content-Type","application/json;charset=UTF-8");
            InputStream inputStream = connection.getInputStream();
            // 设置 UTF_8 否则中文乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            String value = String.valueOf(result);
//            String value2 = new String(value.getBytes(StandardCharsets.UTF_8));
            System.out.println("value");
            System.out.println("value");
            System.out.println("value");
            System.out.println(value);
            System.out.println("value");
            System.out.println("value");
            System.out.println("value");
            VideoListBean videoListBean = gson.fromJson(value, VideoListBean.class);
            //  VideoListBean videoListBean = JSON.parseObject(value, VideoListBean.class);
            if (videoListBean != null && videoListBean.data != null && videoListBean.data.hotPlayMovies != null && videoListBean.data.hotPlayMovies.size() > 0) {
                return ResultUtils.convertSuccessResponse(videoListBean.data);
            } else {
                return ResultUtils.convertErrorResponse(null, "请求异常");
            }
            //  return ResultUtils.convertErrorResponse(null,"请求异常");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //https://www.hangge.com/blog/cache/detail_2516.html
    // https://www.hangge.com/blog/cache/detail_2513.html
    // https://www.hangge.com/blog/cache/detail_2513.html
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test/movies")
    public BaseBean<VideoListBean.DataDTO> getMovies2() {
        String url = new String(Base64.getUrlDecoder().decode(HttpUrl.MOVIE_LIST_URL));
        String str = restTemplate.getForObject(url, String.class);
        System.out.println(str);
        // Gson gson = new Gson();
        // VideoListBean videoListBean = gson.fromJson(str, VideoListBean.class);
        VideoListBean videoListBean = JsonUtil.toDataBean(str, VideoListBean.class);
        //  VideoListBean videoListBean = JSON.parseObject(value, VideoListBean.class);
        if (videoListBean != null && videoListBean.data != null && videoListBean.data.hotPlayMovies != null && videoListBean.data.hotPlayMovies.size() > 0) {
            return ResultUtils.convertSuccessResponse(videoListBean.data);
        } else {
            return ResultUtils.convertErrorResponse(null, "请求异常");
        }
    }


    @PostMapping("/test1/login")
    public BaseBean<Boolean> loginToServer(@RequestBody UserInfo userInfo) {
        String userId = userInfo.getUserId();
        String userPwd = userInfo.getUserPwd();
        if (!UserCacheManager.getInstance().checkUserExist(userId)) {
            return ResultUtils.convertSuccessResponse(false, "账号不存在！");
        }
        if (UserCacheManager.getInstance().checkUserAndPwd(userId, userPwd)) {
            return ResultUtils.convertSuccessResponse(true, "登录成功");
        } else {
            return ResultUtils.convertSuccessResponse(false, "用户名或密码不正确");
        }
    }
}
