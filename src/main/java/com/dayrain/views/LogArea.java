package com.dayrain.views;

import com.dayrain.component.ConfigHolder;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

/**
 * 打印日志的组件
 * 每个服务都有各自的组件
 * @author peng
 * @date 2021/11/8
 */
public class LogArea extends TextArea {
    private String serverName;

    public LogArea() {
        createView();
    }

    public void createView() {
        this.setEditable(false);
        this.setFont(Font.font("Microsoft YaHei", 16));
        this.setPrefWidth(582);
        this.setPrefHeight(ConfigHolder.get().getHeight());
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
