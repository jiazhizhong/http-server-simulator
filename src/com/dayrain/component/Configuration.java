package com.dayrain.component;

import java.util.List;

public class Configuration {

    private String projectName = "HTTP SERVER 模拟器";

    private int width;

    private int height;

    private int stringLen;

    private int intLen;

    private List<ServerConfig>serverConfigs;

    public Configuration() {
    }

    public Configuration(int width, int height, int stringLen, int intLen) {
        this.width = width;
        this.height = height;
        this.stringLen = stringLen;
        this.intLen = intLen;
    }

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

    public int getStringLen() {
        return stringLen;
    }

    public void setStringLen(int stringLen) {
        this.stringLen = stringLen;
    }

    public int getIntLen() {
        return intLen;
    }

    public void setIntLen(int intLen) {
        this.intLen = intLen;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "projectName='" + projectName + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", stringLen=" + stringLen +
                ", intLen=" + intLen +
                ", serverConfigs=" + serverConfigs +
                '}';
    }
}
