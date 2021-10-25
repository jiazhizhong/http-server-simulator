package com.dayrain.entity;

public class ConfigurationHolder {

    private static Configuration configuration;

    public static void init() {
        configuration = new Configuration();
    }

    public static void reload(Configuration config) {
        configuration = config;
    }

    public static Configuration get() {
        return configuration;
    }
}
