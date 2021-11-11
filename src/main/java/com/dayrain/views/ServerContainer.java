package com.dayrain.views;

import javafx.scene.layout.VBox;

import java.util.List;
/**
 * server容器
 * @author peng
 * @date 2021/11/11
 */
public class ServerContainer extends VBox {

    private List<ServerPane> serverPanes;

    public ServerContainer(List<ServerPane> serverPanes) {
        this.serverPanes = serverPanes;
        createView();
    }

    public void createView() {
        getChildren().addAll(serverPanes);
    }

    public void refresh() {
        getChildren().removeAll();
        getChildren().addAll(serverPanes);
    }
}
