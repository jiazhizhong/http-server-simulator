package com.dayrain.entity;

import com.dayrain.utils.FileUtils;

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
}
