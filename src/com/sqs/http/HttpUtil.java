package com.sqs.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.SSLContext;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    private int connectionRequestTimeout=5000;//请求超时
    private int connectTimeout=5000;//连接超时
    private int socketTimeout=5000;//通讯超时
    private int readTimeout=5000;//读取超时
	final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static CloseableHttpClient createHttpClient() {

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(SSLContexts.createSystemDefault()))
                .build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();

        return httpclient;
    }
    
    public static CloseableHttpClient createHttpClient1() throws Exception{
    	
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            TrustStrategy anyTrustStrategy = new TrustStrategy(){
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        }catch(Exception e){
            logger.error(e.getMessage());
            throw e;
        }
        
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);

        CloseableHttpClient httpclient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();
        
        return httpclient;
    }

    /**
     * 创建HttpClient对象 Get请求 访问Url
     * @param url param
     * @return
     */
    public String doGetMap(String url, Map<String, String> param) throws Exception{

        String resultString = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            String json=JSON.toJSONString(param);
            logger.info("请求报文："+json);

            httpClient = createHttpClient();// 创建Httpclient对象
            // 创建URI
            URIBuilder builder = new URIBuilder(url);
            if (null != param && !"".equals(param)) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);// 创建Http Get请求
            httpGet.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(socketTimeout)
                    .build());

            response = httpClient.execute(httpGet);// 执行http请求
            if (response.getStatusLine().getStatusCode() == 200) {// 判断返回状态是否为200
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
                logger.info("返回报文："+resultString);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            if(null != response){
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("流关闭异常",e);
                }
            }
        }
        return resultString;
    }
    /**
     * 创建HttpClient对象 Post请求 访问Url
     * @param url param
     * @return
     */
    public  String doPostMap(String url, Map<String, String> param) throws Exception {

        String resultString = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            String json=JSON.toJSONString(param);
            logger.info("请求报文："+json);

            httpClient = createHttpClient();// 创建Httpclient对象
            HttpPost httpPost = new HttpPost(url);// 创建Http Post请求
            httpPost.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(socketTimeout)
                    .build());
            // 创建请求内容
            if (null != param && !"".equals(param)) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);// 模拟表单
                httpPost.setEntity(entity);
            }

            response = httpClient.execute(httpPost);// 执行http请求
            if (response.getStatusLine().getStatusCode() == 200) {// 判断返回状态是否为200
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
                logger.info("返回报文："+resultString);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            if(null != response){
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("流关闭异常",e);
                }
            }
        }
        return resultString;
    }
    /**
     * 创建HttpClient对象 Post请求 访问Url
     * @param url json
     * @return
     */
    public String doPostJson(String url, String json) throws Exception{

        String resultString = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            logger.info("请求报文："+json);

            httpClient = createHttpClient();// 创建Httpclient对象
            HttpPost httpPost = new HttpPost(url); // 创建Http Post请求
            httpPost.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(socketTimeout)
                    .build());
            // 创建请求内容
            if (null != json && !"".equals(json)) {
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                httpPost.setEntity(entity);
            }

            response = httpClient.execute(httpPost);// 执行http请求
            if (response.getStatusLine().getStatusCode() == 200) {// 判断返回状态是否为200
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
                logger.info("返回报文："+resultString);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            if(null != response){
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("流关闭异常",e);
                }
            }
        }
        return resultString;
    }
    /**
     * 创建URL对象 Post请求 访问Url
     * @param apiUrl param
     * @return
     */
    public String doPostMap1(String apiUrl,Map<String, String> param) throws Exception{

        String resultString = "";
        DataOutputStream out = null;
        BufferedReader in = null;
        try {
            String json=JSON.toJSONString(param);
            logger.info("请求报文："+json);

            // 组织请求参数
            StringBuilder postBody = new StringBuilder();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                postBody.append(key).append("=").append(URLEncoder.encode(value.toString(), "utf-8")).append("&");
            }
            if (null != param && !"".equals(param)) {
                postBody.deleteCharAt(postBody.length() - 1);
            }

            URL url = new URL(apiUrl);// 创建URL对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 打开HttpURLConnection连接
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setRequestMethod("POST");// 设置请求方式 GET POST
            conn.setDoOutput(true);// 设置是否向conn输出数据
            conn.setDoInput(true);// 设置是否从conn读数据
            conn.setUseCaches(false);// 设置是否使用缓存 Post不使用缓存

            out = new DataOutputStream(conn.getOutputStream());
            out.write(postBody.toString().getBytes());
            out.flush();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {// 判断返回状态是否为200
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line).append("\n");
                }
                logger.info("返回报文："+result.toString());
                resultString=result.toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("输入流关闭异常"+e);
                }
            }
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("输出流关闭异常"+e);
                }
            }
        }
        return resultString;
    }
    /**
     * 创建URL对象 Post请求 访问Url
     * @param apiUrl sendJson
     * @return
     */
    public String doPostJson1(String apiUrl,String sendJson) throws Exception{

        String resultString = "";
        DataOutputStream out = null;
        BufferedReader in = null;
        try {
            logger.info("请求报文："+sendJson);

            URL url = new URL(apiUrl);// 创建URL对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 打开HttpURLConnection连接
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setRequestMethod("POST");// 设置请求方式 GET POST
            conn.setRequestProperty("Content-type","application/json;charset=utf-8");// 设置请求内容格式 Json
            conn.setDoOutput(true);// 设置是否向conn输出数据
            conn.setDoInput(true);// 设置是否从conn读数据
            conn.setUseCaches(false);// 设置是否使用缓存 Post不使用缓存

            out = new DataOutputStream(conn.getOutputStream());
            out.write(sendJson.getBytes());
            out.flush();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {// 判断返回状态是否为200
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line).append("\n");
                }
                logger.info("返回报文："+result.toString());
                resultString=result.toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        } finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("输入流关闭异常"+e);
                }
            }
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("输出流关闭异常"+e);
                }
            }
        }
        return resultString;
    }
}