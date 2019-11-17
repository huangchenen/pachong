package com.util;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
    private static Properties pro;

    static{
        pro = new Properties();
        try {
            pro.load(ConfigUtils.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        String property = pro.getProperty(key);
        return property;
    }

    public static void main(String[] args) {
        String property = ConfigUtils.getProperty("jdbc.username");
        System.out.println(property);
    }
}
