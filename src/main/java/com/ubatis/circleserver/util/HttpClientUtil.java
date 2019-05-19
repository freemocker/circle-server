package com.ubatis.circleserver.util;

import com.ubatis.circleserver.util.threadpool.ThreadPool;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lance on 2017/11/1.
 */
public class HttpClientUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final int TIMEOUT_IN_MILLIONS = 10000;

    /**
     * 同步get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder urlWithParams = new StringBuilder(url);
        if (params != null) {
            int flag = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlWithParams.append((flag++ == 0 ? "?" : "&") + entry.getKey() + "=" + entry.getValue());
            }
        }
//        logger.info(urlWithParams.toString());
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(urlWithParams.toString());
        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                httpget.setHeader(e.getKey(), e.getValue());
            }
        }
        //配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT_IN_MILLIONS)
                .setConnectTimeout(TIMEOUT_IN_MILLIONS)
                .setSocketTimeout(TIMEOUT_IN_MILLIONS).build();
        httpget.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        String jsonStr = null;
        try {
            response = httpclient.execute(httpget);
            // logger.info("StatusCode -> {}" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            jsonStr = EntityUtils.toString(entity, "UTF-8");
            // logger.info("jsonStr -> {}" + jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpget.releaseConnection();
        return jsonStr;
    }

    public static byte[] doGetBin(String url, Map<String, Object> params) {
        StringBuilder urlWithParams = new StringBuilder(url);
        if (params != null) {
            int flag = 0;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                urlWithParams.append((flag++ == 0 ? "?" : "&") + entry.getKey() + "=" + entry.getValue());
            }
        }
//        logger.info(urlWithParams.toString());
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(urlWithParams.toString());
        //配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT_IN_MILLIONS)
                .setConnectTimeout(TIMEOUT_IN_MILLIONS)
                .setSocketTimeout(TIMEOUT_IN_MILLIONS).build();
        httpget.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        String jsonStr = null;
        try {
            response = httpclient.execute(httpget);
            byte[] data = EntityUtils.toByteArray(response.getEntity());
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpget.releaseConnection();
        return null;
    }

    // 异步get请求
    public static void  doGetAsync(String url, Map<String, String> params, final CallBack callback) {
        ThreadPool.getInstance().getExecutor().execute(()->{
            try {
                String ret = doGet(url, params, null);
                callback.onRequestComplete(ret);
            }catch (Exception e){
                e.printStackTrace();
                callback.onRequestComplete(null);
            }
        });
    }

    /**
     * 同步post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, Object> params) {
        List<NameValuePair> params2send = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                params2send.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
//        System.out.println(params2send);
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT_IN_MILLIONS)
                .setConnectTimeout(TIMEOUT_IN_MILLIONS)
                .setSocketTimeout(TIMEOUT_IN_MILLIONS).build();
        httppost.setConfig(requestConfig);
        String jsonStr = null;
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params2send));
            CloseableHttpResponse response = httpclient.execute(httppost);
//        System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            jsonStr = EntityUtils.toString(entity, "UTF-8");
//        System.out.println(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        httppost.releaseConnection();
        return jsonStr;
    }

    public static void  doPostAsync(String url, Map<String, Object> params, final CallBack callback) {
        ThreadPool.getInstance().getExecutor().execute(()->{
            try {
                String ret = doPost(url, params);
                callback.onRequestComplete(ret);
            }catch (Exception e){
                e.printStackTrace();
                callback.onRequestComplete(null);
            }
        });
    }

    /**
     * post json
     *
     * @param sendurl
     * @param json
     * @return
     */
    public static String postJson(String sendurl, String json) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(sendurl);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT_IN_MILLIONS)
                .setConnectTimeout(TIMEOUT_IN_MILLIONS)
                .setSocketTimeout(TIMEOUT_IN_MILLIONS).build();
        post.setConfig(requestConfig);
        StringEntity myEntity = new StringEntity(json, ContentType.APPLICATION_JSON);// 构造请求数据
        post.setEntity(myEntity);// 设置请求体
        String responseContent = null; // 响应内容
        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (client != null)
                        client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseContent;
    }

    public static void  doPostJsonAsync(String url, String json, final CallBack callback) {
        ThreadPool.getInstance().getExecutor().execute(()->{
            try {
                String ret = postJson(url, json);
                callback.onRequestComplete(ret);
            }catch (Exception e){
                e.printStackTrace();
                callback.onRequestComplete(null);
            }
        });
    }

    /**
     * 获取requestBody里面的body
     *
     * @param request
     * @return
     */
    public static String getRequestBody(HttpServletRequest request) {
        String json = null;
        try {
            final InputStream instream = request.getInputStream();
            try {
                final StringBuilder sb = new StringBuilder();
                final char[] tmp = new char[1024];
                final Reader reader = new InputStreamReader(instream, "UTF-8");
                int l;
                while ((l = reader.read(tmp)) != -1) {
                    sb.append(tmp, 0, l);
                }
                json = sb.toString();
            } finally {
                instream.close();
            }
        } catch (IOException e) {
            return json;
        }
        return json;
    }

    public static byte[] postJsonGetBin(String url, String json) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT_IN_MILLIONS)
                .setConnectTimeout(TIMEOUT_IN_MILLIONS)
                .setSocketTimeout(TIMEOUT_IN_MILLIONS).build();
        post.setConfig(requestConfig);
        StringEntity myEntity = new StringEntity(json, ContentType.APPLICATION_JSON);// 构造请求数据
        post.setEntity(myEntity);// 设置请求体
        String responseContent = null; // 响应内容
        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                byte[] data = EntityUtils.toByteArray(response.getEntity());
                return data;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface CallBack {
        void onRequestComplete(String result);
    }

}
