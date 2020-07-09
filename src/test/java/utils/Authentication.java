package utils;

import com.alibaba.fastjson.JSONPath;
import constants.Constants;


import java.util.HashMap;
import java.util.Map;

/**
 * 通过jsonpath,从响应体中取出表达式需要的值，存放到Map中
 * @author 向阳
 * @date 2020/6/19-17:22
 */
public class Authentication {
    //定义用户自定义变量，用于存放token、memner_id等
    public static Map<String,Object> ENV=new HashMap<String, Object>();

    /**
     *  通过jsonpath,从响应体中取出
     * @param body  响应体
     * @param expression  json表达式
     * @param key   存储鉴权的key
     */
    public static void json2ENV(String body,String expression,String key){
        //通过jsonpath,从响应体中取出表达式需要的值
        Object obj = JSONPath.read(body,expression);
        //把token存到用作鉴权的map中
        if(obj != null) {
            //把需要的自定义变量值放到map中
            Object object = Authentication.ENV.put(key, obj);
            System.out.println(key+"======"+obj);
        }
    }

    /**
     * 根据请求方式json/form，添加请求头中的请求方式
     * @param contentType
     */
    public static void addHeader(String contentType){

        if("json".equals(contentType)){
            Constants.HEADERS.put("Content-Type", "application/json");
        }else if("form".equals(contentType)){
            Constants.HEADERS.put("Content-Type", "application/x-www-form-urlencoded");
        }
    }

}
