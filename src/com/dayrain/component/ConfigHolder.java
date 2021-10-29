package com.dayrain.component;

import com.dayrain.utils.FileUtils;
import com.fasterxml.jackson.databind.util.BeanUtil;

import java.awt.*;

public class ConfigHolder {
    private static Configuration configuration;

    private ConfigHolder(){}

    public synchronized static Configuration init() {
        configuration = FileUtils.load();
        return configuration;
    }

    public static void save() {
        FileUtils.saveConfig(configuration);
    }

    public static Configuration get() {
        return configuration;
    }

    public static void replace(Configuration config) {
        configuration = config;
    }
}
