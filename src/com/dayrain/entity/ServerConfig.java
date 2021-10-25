package com.dayrain.entity;

import java.util.List;

public class ServerConfig {
    /**
     * 服务名
     */
    private String serverName;
    /**
     * 端口
     */
    private int port;
    /**
     * 请求url集合
     */
    private List<ServerUrl>serverUrls;

    public ServerConfig(String serverName, int port, List<ServerUrl>serverUrls) {
        this.serverName = serverName;
        this.port = port;
        this.serverUrls = serverUrls;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public List<ServerUrl> getServerUrls() {
        return serverUrls;
    }

    public void setServerUrls(List<ServerUrl> serverUrls) {
        this.serverUrls = serverUrls;
    }
}
