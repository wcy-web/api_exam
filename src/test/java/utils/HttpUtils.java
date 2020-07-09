package utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;


/**
 * Http请求的工具类
 * @author 向阳
 * @date 2020/6/3-18:47
 */
public class HttpUtils {

      private  static Logger logger=Logger.getLogger(HttpUtils.class);

      public static HttpResponse call(String url,String params,String type,String contentType,Map<String,String> headers){

          if("json".equals(contentType)) {
            if ("get".equals(type)) {
                return getRequst(url,headers);
            } else if ("post".equals(type)) {
               return postRequst(url,params,headers);
            } else if ("patch".equalsIgnoreCase(type)) {
                return patchRequest(url, params,headers);
            } else if ("put".equals(type)) {
                return putRequest(url, params,headers);
            } else if ("delete".equals(type)) {
                return deleteRequest(url, params,headers);
            }
        }else if("form".equals(contentType)){
              params=josn2KeyValue(params);
            return postRequst(url, params,headers);
        }
        return null;
    }


    /**
     * 发送get请求
     * @param url
     */
  public static HttpResponse getRequst(String url,Map<String,String> headers)  {
            try {
                //1、创建request请求,携带了请求方法+url
                HttpGet get = new HttpGet(url);
                //2、设置请求头
               setHeader(headers, get);
//                get.addHeader("Content-Type", "application/json");
                //3、创建请求客户端
                HttpClient client = HttpClients.createDefault();
                //4、发送请求
                HttpResponse response = client.execute(get);
//                printResponse(response);
                return response;
            }catch (Exception e){
                e.getStackTrace();
            }
            return null;
  }


    /**
     * 发送post请求
     * @param url
     * @param params
     */
    public static HttpResponse postRequst(String url,String params,Map<String,String> headers){
        try {
            HttpPost post=new HttpPost(url);
            setHeader(headers, post);
            StringEntity entity=new StringEntity(params,"utf-8");
            post.setEntity(entity);
            //创建请求客户端
            HttpClient client = HttpClients.createDefault();
            //发送请求，请得到响应
            HttpResponse response = client.execute(post);
//            printResponse(response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 发送from表单请求
     * @param url
     * @param params
     * @param
     * @return
     */
//    public static HttpResponse formPost(String url,String params,Map<String,String> headers){
//        try {
//            HttpPost post = new HttpPost(url);
//            setHeader(headers, post);
////            post.addHeader("X-Lemonban-Media-Type", "lemonban.v1");
////            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
//            String result = josn2KeyValue(params);
//            StringEntity entity = new StringEntity(result, "utf-8");
//            post.setEntity(entity);
//            HttpClient client = HttpClients.createDefault();
//            HttpResponse response = client.execute(post);
////          printResponse(response);
//            return response;
//        }catch (Exception e){
//            e.getStackTrace();
//        }
//        return null;
//    }

    /**
     * 发送patch请求
     * @param url
     * @param params
     * @throws Exception
     */
    public static HttpResponse patchRequest(String url,String params,Map<String,String> headers){
        try {
        HttpPatch patch=new HttpPatch(url);
        setHeader(headers, patch);
//      patch.addHeader("X-Lemonban-Media-Type", "lemonban.v1");
//      patch.addHeader("Content-Type", "application/json");
        StringEntity entity=new StringEntity(params,"utf-8");
        patch.setEntity(entity);
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = null;
        response = client.execute(patch);
//            printResponse(response);
        return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送put请求
     * @param url
     * @param params
     * @throws Exception
     */
    public static HttpResponse putRequest(String url,String params,Map<String,String> headers){
        try {
            HttpPut put = new HttpPut(url);
            setHeader(headers, put);
//            put.addHeader("X-Lemonban-Media-Type", "lemonban.v1");
//            put.addHeader("Content-Type", "application/json");
            StringEntity entity = new StringEntity(params, "utf-8");
            put.setEntity(entity);
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(put);
//            printResponse(response);
            return response;
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }

    /**
     * 发送delete请求
     * @param url
     * @param params
     * @throws Exception
     */
    public static HttpResponse deleteRequest(String url,String params,Map<String,String> headers){
        try {
            HttpDelete delete = new HttpDelete(url);
            setHeader(headers, delete);
//            delete.addHeader("X-Lemonban-Media-Type", "lemonban.v1");
//            delete.addHeader("Content-Type", "application/json");
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(delete);
//            printResponse(response);
            return response;
        }catch (Exception e){
            e.getStackTrace();
        }
        return  null;
    }

    /**
     * 解析响应头、code、响应体
     * @param response
     * @throws IOException
     */
    public static String printResponse(HttpResponse response){
        //5、接收相应，并获取响应头、code、响应体
        try {
        Header[] allHeaders = response.getAllHeaders();
        int code = response.getStatusLine().getStatusCode();
        //6、格式化相应体（body）
        HttpEntity entity = response.getEntity();
        String body =EntityUtils.toString(entity);
            logger.info(Arrays.toString(allHeaders));
            logger.info(code);
            logger.info(body);
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 设置请求头
     * @param headers   请求头
     * @param request   请求方式
     */
    public static void setHeader(Map<String, String> headers, HttpRequest request) {
        Set<String> keySet = headers.keySet();
        //循环设置请求头
        for (String key : keySet) {
            String value = headers.get(key);
            request.setHeader(key,value);
        }
    }

    //将json字符串转换成key=value格式
    public static String josn2KeyValue(String params){
        Map<String,String> map = JSONObject.parseObject(params, Map.class);
        Set<String> keySet = map.keySet();
        String result="";
        for (String key : keySet) {
            String value=map.get(key);
            result += key+"="+value+"&";
        }
        result=result.substring(0,result.length()-1);
        return result;
    }


}
