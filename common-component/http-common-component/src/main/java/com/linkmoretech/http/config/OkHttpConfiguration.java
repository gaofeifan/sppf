package com.linkmoretech.http.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @Author: alec
 * Description:
 * @date: 13:58 2019-05-20
 */
@Configuration
public class OkHttpConfiguration {

    @Bean
    public OkHttpClient okHttpClient () {

       return new OkHttpClient.Builder().retryOnConnectionFailure(false)
               .sslSocketFactory(sslSocketFactory(), x509TrustManager())
               .connectionPool(connectionPool())
               .connectTimeout(100, TimeUnit.SECONDS)
               .readTimeout(100, TimeUnit.SECONDS)
               .writeTimeout(100, TimeUnit.SECONDS).build();
    }

    @Bean
    public ConnectionPool connectionPool () {
        Integer maxIdleConnections = 200;
        Integer keepAliveDuration = 5;
        return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES );
    }

    @Bean
    public SSLSocketFactory sslSocketFactory(){
       try {
           SSLContext sslContext = SSLContext.getInstance("TLS");
           sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
           return sslContext.getSocketFactory();
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       } catch (KeyManagementException e) {
           e.printStackTrace();
       }
       return null;
    }

    @Bean
    public X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }
}
