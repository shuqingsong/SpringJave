package com.sqs.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
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
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.*;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;
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
    public static CloseableHttpClient createHttpClient1() throws Exception {
    	
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
    public String httpClientGet(String url, Map<String, String> param) throws Exception {

        String resultString = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            String json=JSON.toJSONString(param);
            logger.info("请求报文："+json);

            httpClient = createHttpClient();// 创建Httpclient对象
            // 创建URI 方式一
            URIBuilder builder = new URIBuilder(url);
            if (null != param && !param.isEmpty()) {
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    builder.addParameter(key, value);
                }
            }
            URI uri = builder.build();

            // 创建URI 方式二
//            StringBuilder paramBody = new StringBuilder();
//            if (null != param && !param.isEmpty()) {
//                for (Map.Entry<String, String> entry : param.entrySet()) {
//                    String key = entry.getKey();
//                    String value = entry.getValue();
//                    paramBody.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
//                }
//                paramBody.deleteCharAt(postBody.length() - 1);
//                url=url+"?"+paramBody.toString();
//            }
//            URI uri = new URI(url);

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
    public  String httpClientPost(String url, Map<String, String> param) throws Exception {

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
            // 组织请求内容
            if (null != param && !param.isEmpty()) {
                //方式一
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    paramList.add(new BasicNameValuePair(key, value));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);// 模拟表单
                httpPost.setEntity(entity);

                //方式二
//                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//                for (Map.Entry<String, String> entry : param.entrySet()) {
//                    String key = entry.getKey();
//                    String value = entry.getValue();
//                    builder.addPart(key,new StringBody(value,ContentType.MULTIPART_FORM_DATA));
//                }
//                HttpEntity entity = builder.build();
//                httpPost.setEntity(entity);
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
    public String httpClientPostJson(String url, String json) throws Exception {

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
            // 组织请求内容
            if (null != json && !json.isEmpty()) {
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
     * 默认的TrustManager实现，不安全
     */
    public static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
    public static HttpURLConnection createHttpURLConnection(String uri) throws Exception {

        URL url = new URL(uri);// 创建URL对象
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 打开HttpURLConnection连接
        return conn;
    }
    public static HttpsURLConnection createHttpsURLConnection1(String uri, KeyStore keyStore, String keyStorePassword, KeyStore trustStore) throws Exception {

        KeyManager[] keyManagers = null;
        TrustManager[] trustManagers = null;
        if (keyStore != null) {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
            keyManagers = keyManagerFactory.getKeyManagers();
        }
        if (trustStore != null) {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(trustStore);
            trustManagers = trustManagerFactory.getTrustManagers();
        } else {
            trustManagers = new TrustManager[] { new DefaultTrustManager()};
        }

        //创建SSLContext对象 并使用我们指定的信任管理器初始化
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");//参数为协议
        sslContext.init(null, trustManagers, new SecureRandom());

        URL url = new URL(uri);// 创建URL对象
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();// 打开HttpsURLConnection连接
        conn.setSSLSocketFactory(sslContext.getSocketFactory());//设置其SSLSocketFactory对象
        //验证URL的主机名和服务器的标识主机名是否匹配
        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                System.out.println("WARNING: Hostname is not matched for cert.");
                return true;
            }
        });
        return conn;
    }
    /**
     * 创建URL对象 Get请求 访问Url param中中文参数需URLEncoder.encode(value.toString(), "utf-8")编码
     * @param url param
     * @return
     */
    public String urlConnectionGet(String url,Map<String, String> param) throws Exception {

        String resultString = "";
        DataOutputStream out = null;
        BufferedReader in = null;
        try {
            String json=JSON.toJSONString(param);
            logger.info("请求报文："+json);

            // 组织请求内容
            StringBuilder paramBody = new StringBuilder();
            if (null != param && !param.isEmpty()) {
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    paramBody.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
                }
                paramBody.deleteCharAt(paramBody.length() - 1);
                url=url+"?"+paramBody.toString();
            }

            HttpURLConnection conn = createHttpURLConnection(url);// 打开HttpURLConnection连接
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setRequestMethod("GET");// 设置请求方式 GET POST
            conn.setDoInput(true);// 设置是否从conn读数据
            conn.setUseCaches(false);// 设置是否使用缓存

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
     * 创建URL对象 Post请求 访问Url param中中文参数需URLEncoder.encode(value.toString(), "utf-8")编码
     * @param url param
     * @return
     */
    public String urlConnectionPost(String url,Map<String, String> param) throws Exception {

        String resultString = "";
        DataOutputStream out = null;
        BufferedReader in = null;
        try {
            String json=JSON.toJSONString(param);
            logger.info("请求报文："+json);

            // 组织请求内容
            StringBuilder postBody = new StringBuilder();
            if (null != param && !param.isEmpty()) {
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    postBody.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
                }
                postBody.deleteCharAt(postBody.length() - 1);
            }

            HttpURLConnection conn = createHttpURLConnection(url);// 打开HttpURLConnection连接
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
     * 创建URL对象 Post请求 访问Url sendJson中中文参数需URLEncoder.encode(value.toString(), "utf-8")编码
     * @param url sendJson
     * @return
     */
    public String urlConnectionPostJson(String url,String sendJson) throws Exception {

        String resultString = "";
        DataOutputStream out = null;
        BufferedReader in = null;
        try {
            logger.info("请求报文："+sendJson);

            HttpURLConnection conn = createHttpURLConnection(url);// 打开HttpURLConnection连接
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