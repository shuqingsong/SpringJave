package com.sqs.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.net.ssl.SSLContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpUtil {
	
	final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
    public static String downloadFile(String src_file, String dest_file) throws Throwable {
    	
        try (CloseableHttpClient httpclient = createHttpClient()) {
            HttpGet httpget = new HttpGet(src_file);
            httpget.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(5000) //
                    .setConnectTimeout(15000) //
                    .setSocketTimeout(15000) //
                    .build());
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                org.apache.http.HttpEntity entity = response.getEntity();
                File desc = new File(dest_file);
                File folder = desc.getParentFile();
                folder.mkdirs();
                try (InputStream is = entity.getContent(); //
                     OutputStream os = new FileOutputStream(desc)) {
                    StreamUtils.copy(is, os);
                }
            }catch(Throwable e){
            	logger.error(e.getMessage());
                throw new Throwable("文件下载失败......", e);
            }
        }
        return dest_file;
    }

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
    
    public static CloseableHttpClient createHttpClient1() {
    	
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
        }
        
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);

        CloseableHttpClient httpclient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();
        
        return httpclient;
    }
}