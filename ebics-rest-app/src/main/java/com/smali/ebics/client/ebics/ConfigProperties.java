package com.smali.ebics.client.ebics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
    Properties properties = new Properties();

    public ConfigProperties(File file) throws IOException {
        properties.load(new FileInputStream(file));
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("property not set or empty: " + key);
        }
        return value.trim();
    }

    @Override
    public String toString() {
        return properties.toString();
    }
    
}
