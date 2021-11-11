package com.dayrain.views;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.ConsoleLog;
import com.dayrain.component.ServerConfig;
import com.dayrain.component.ServerThreadHolder;
import com.dayrain.style.IconFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
/**
 * 主页
 * @author peng
 * @date 2021/11/11
 */
public class HomeView {

    private final Stage primaryStage;

    public HomeView() {
        this.primaryStage = ViewHolder.getPrimaryStage();
    }
    /**
     * 启动程序
     */
    public void start() {
        VBox vBox = new VBox();

        //菜单栏
        MenuBarView menuBar = new MenuBarView();
        SplitPane splitPane = new SplitPane();
        //日志
        LogArea logArea = new LogArea();
        ConsoleLog.initTextArea(logArea);
        ViewHolder.setLogArea(logArea);

        //server
        ServerContainer serverContainer = initServer();
        ViewHolder.setServerContainer(serverContainer);
        splitPane.getItems().addAll(serverContainer, logArea);
        splitPane.setDividerPositions(0.55f, 0.45f);
        vBox.getChildren().addAll(menuBar, splitPane);

        primaryStage.setTitle(ConfigHolder.get().getProjectName());
        primaryStage.setScene(new Scene(vBox));
        primaryStage.setWidth(ConfigHolder.get().getWidth());
        primaryStage.setHeight(ConfigHolder.get().getHeight());
        primaryStage.getIcons().add(IconFactory.getIcon());

        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            ConfigHolder.save();
            ServerThreadHolder.stopAll();
            Platform.exit();
        });
    }

    /**
     * 初始化服务列表
     * @return 服务的容器
     */
    private ServerContainer initServer() {
        List<ServerConfig> serverConfigs = ConfigHolder.get().getServerConfigs();
        List<ServerPane>serverPanes = new ArrayList<>();
        if(serverConfigs != null) {
            for (ServerConfig serverConfig : serverConfigs) {
                ServerPane serverPane = new ServerPane(serverConfig);
                serverPanes.add(serverPane);
            }
        }
        return new ServerContainer(serverPanes);
    }

    public void restart() {
        //停止所有线程
        ServerThreadHolder.stopAll();
        start();
    }
}
