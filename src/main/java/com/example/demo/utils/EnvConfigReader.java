package com.example.demo.utils;

public class EnvConfigReader {
    
    /**
     * This function returns the environment variable matched by key
     * @param key
     * @return string value or null if key not exists
     */
    public static String getConfig(String key){

        String value = System.getenv(key);
        if (value != null) {
          return value;   
        }

        return null;
    }

    public static String getConfig(String key, String defaultVal){

        String value = System.getenv(key);
        if (value != null) {
          return value;   
        }

        return defaultVal;
    }

}
