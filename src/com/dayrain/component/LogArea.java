package com.dayrain.component;

import javafx.scene.control.TextArea;

public class LogArea extends TextArea {
    private String serverName;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
