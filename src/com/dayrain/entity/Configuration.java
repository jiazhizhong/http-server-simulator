package com.dayrain.entity;

import java.util.List;

public class Configuration {

    private List<ServerConfig>serverConfigs;

    public List<ServerConfig> getServerConfigs() {
        return serverConfigs;
    }

    public void setServerConfigs(List<ServerConfig> serverConfigs) {
        this.serverConfigs = serverConfigs;
    }
}
