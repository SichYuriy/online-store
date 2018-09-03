package com.gmail.at.sichyuriyy.onlinestore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Yuriy on 4/9/2017.
 */
public enum PropertiesLoader {

    INSTANCE;

    private static final String APP_PROPERTIES = "app.properties";

    private Properties appProperties;

    PropertiesLoader() {
        appProperties = loadProperties(APP_PROPERTIES);
    }

    public Properties getAppProperties() {
        return appProperties;
    }

    public Properties loadProperties(String fileName) {
        Properties properties;
        try (InputStream stream = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties = loadProperties(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private Properties loadProperties(InputStream is) {
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}
