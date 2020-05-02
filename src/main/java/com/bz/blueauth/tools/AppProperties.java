package com.bz.blueauth.tools;

import java.util.Properties;

public class AppProperties {

    private static AppProperties _instance;
    Properties mainProp;

    private AppProperties(){
        Properties prop = new Properties();
        try {
            prop.load(AppProperties.class.getClassLoader().getResourceAsStream("application.properties"));
            String activeEnv = prop.getProperty("spring.profiles.active");
            this.mainProp = new Properties();
            this.mainProp.load(AppProperties.class.getClassLoader().getResourceAsStream(String.format("application-%s.properties", activeEnv)));
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
    }

    public String get(String key) {
        return this.mainProp.getProperty(key);
    }

    public static AppProperties getInstance(){
        if(_instance == null) 
            return new AppProperties();
        
        return _instance;
    }

}