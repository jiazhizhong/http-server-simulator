package com.dayrain.entity;

import java.util.List;

public class Configuration {

    private String projectName;

    private int width;

    private int height;

    private List<ServerConfig>serverConfigs;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ServerConfig> getServerConfigs() {
        return serverConfigs;
    }

    public void setServerConfigs(List<ServerConfig> serverConfigs) {
        this.serverConfigs = serverConfigs;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
