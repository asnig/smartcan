package com.ckguys.test;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author XYQ
 */
class ResourceBundleTest {

    @Test
    void fun1() throws InterruptedException {
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    @Test
    void testPost() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        String baseUri = "http://localhost:8080/AnalyseServlet?method=storeState&state=";
        while (true) {
            try {
                Thread.sleep(500);
                int r = new Random().nextInt(101);
                String finalUri = baseUri + r;
                HttpPost httpGet = new HttpPost(finalUri);
                httpClient.execute(httpGet);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
