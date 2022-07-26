package com.example.ldpserver.requestutils;

import com.example.ldpserver.model.BaseBean;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class ResultUtils {

    public final static int SUCCESS = 0;
    public final static int ERROR = -1;


    public static <T> BaseBean<T> convertSuccessResponse(T data) {
        return convertSuccessResponse(data,"");
    }
    public static <T> BaseBean<T> convertSuccessResponse(T data,String message) {
        BaseBean<T> baseBean = new BaseBean<>();
        baseBean.setCode(SUCCESS);
        baseBean.setMessage(message);
        baseBean.setData(data);
        return baseBean;
    }

    public static <T> BaseBean<T> convertErrorResponse(T data, String message) {
        BaseBean<T> baseBean = new BaseBean<>();
        baseBean.setCode(ERROR);
        baseBean.setMessage(message);
        baseBean.setData(data);
        return baseBean;
    }
}
