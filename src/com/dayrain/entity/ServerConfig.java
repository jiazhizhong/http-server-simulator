package com.dayrain.entity;

public class ServerConfig {

    private int port = 8081;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerConfig(int port) {
        this.port = port;
    }
}
