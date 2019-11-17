package com.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author huangchenen
 */

public class PageDownLoadUtils {

    public static String getPageContent(String url){

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        try {
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity,"GBK");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) {

        String content = getPageContent("http://kaijiang.500.com/shtml/ssq/19128.shtml");
        System.out.println(content);

    }
}
