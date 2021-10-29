package com.dayrain.views;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.Configuration;
import com.dayrain.component.ConsoleLog;
import com.dayrain.component.LogArea;
import com.dayrain.component.ServerConfig;
import com.dayrain.component.ServerUrl;
import com.dayrain.handle.AddServerHandler;
import com.dayrain.handle.AddUrlHandler;
import com.dayrain.handle.DeleteServerHandler;
import com.dayrain.handle.DeleteUrlHandler;
import com.dayrain.handle.ExportConfigHandler;
import com.dayrain.handle.ImportConfigHandler;
import com.dayrain.handle.StartServerHandler;
import com.dayrain.handle.UpdateRandomLenHandler;
import com.dayrain.handle.UpdateServerConfigHandler;
import com.dayrain.handle.UpdateUrlHandler;
import com.dayrain.server.ServerThread;
import com.dayrain.style.ButtonFactory;
import com.dayrain.style.LabelFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage {

    private Stage primaryStage;

    private Configuration configuration = ConfigHolder.init();

    private HashMap<String, ServerThread> threadMap = new HashMap<>();

    private VBox serverContainer;

    private LogArea logArea;

    public HomePage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start() {

        BorderPane borderPane = new BorderPane();

        //菜单栏
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("文件");
        MenuItem menuItem1 = new MenuItem("新建");
        MenuItem menuItem2 = new MenuItem("导入");
        MenuItem menuItem3 = new MenuItem("导出");
        menu1.getItems().addAll(menuItem1, menuItem2, menuItem3);
        Menu menu2 = new Menu("设置");
        MenuItem menuItem21 = new MenuItem("随机值长度");
        menu2.getItems().add(menuItem21);
        Menu menu3 = new Menu("帮助");
        MenuItem menuItem31 = new MenuItem("说明书");
        menuItem31.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://blog.csdn.net/qq_37855749/article/details/121030800"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        MenuItem menuItem32 = new MenuItem("源码地址");
        menuItem32.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/DayRain/http-server-simulator"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        menu3.getItems().addAll(menuItem31, menuItem32);
        menuBar.getMenus().addAll(menu1, menu2, menu3);
        borderPane.setTop(menuBar);

        //渲染服务列表
        this.serverContainer = new VBox();
        refreshServerContainer();

        menuItem1.setOnAction(new AddServerHandler(primaryStage, configuration, this));
        menuItem2.setOnAction(new ImportConfigHandler(primaryStage, this));
        menuItem3.setOnAction(new ExportConfigHandler(primaryStage, configuration));
        menuItem21.setOnAction(new UpdateRandomLenHandler(primaryStage));
        borderPane.setLeft(serverContainer);

        //日志
        logArea = new LogArea();
        logArea.setEditable(false);
        logArea.setFont(Font.font("Microsoft YaHei", 20));
        logArea.setPrefWidth(582);
        logArea.setPrefHeight(600);

        ConsoleLog.setTextArea(logArea);

        borderPane.setRight(logArea);

        menuBar.setBackground(getBackGround());

        Scene scene = new Scene(borderPane);
        primaryStage.setTitle(configuration.getProjectName());
        primaryStage.setScene(scene);
        primaryStage.setWidth(configuration.getWidth());
        primaryStage.setHeight(configuration.getHeight());
        primaryStage.getIcons().add(getIcon());
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> ConfigHolder.save());
    }

    public void drawServerPanel(VBox serverContainer, ServerConfig serverConfig, Stage primaryStage) {

        VBox vBox = new VBox();
        HBox headBox = new HBox();
        Button editButton = ButtonFactory.getButton("修改配置");
        Button openButton = ButtonFactory.getButton("开启服务");
        Button deleteButton = ButtonFactory.getButton("删除服务");
        Button addButton = ButtonFactory.getButton("添加接口");
        Circle statusCircle = new Circle();
        statusCircle.setRadius(10);
        statusCircle.setFill(Color.RED);
        //设置服务启动与关闭
        openButton.setOnAction(new StartServerHandler(openButton, statusCircle, serverConfig, threadMap));
        editButton.setOnAction(new UpdateServerConfigHandler(serverConfig, primaryStage));
        deleteButton.setOnAction(new DeleteServerHandler(serverConfig, configuration, this));
        headBox.getChildren().addAll(openButton, editButton, deleteButton, addButton, statusCircle);
        HBox.setMargin(statusCircle, new Insets(0, 0, 0, 30));
        headBox.setSpacing(20d);
        headBox.setAlignment(Pos.CENTER);
        //添加url
        ListView<ServerUrl> serverUrlListView = drawUrlPanel(serverConfig.getServerUrls(), serverConfig);
        addButton.setOnAction(new AddUrlHandler(serverConfig, serverUrlListView, threadMap, primaryStage));
        vBox.getChildren().addAll(headBox, serverUrlListView);

        vBox.setSpacing(10d);
        VBox.setMargin(headBox, new Insets(10, 0, 0, 0));
        vBox.setPadding(Insets.EMPTY);
        TitledPane titledPane = new TitledPane(serverConfig.getServerName(), vBox);
        titledPane.setFont(Font.font("Microsoft YaHei", 18));
        titledPane.setPrefWidth(600d);
        titledPane.setExpanded(false);
        titledPane.setBackground(getBackGround());
        titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!serverConfig.getServerName().equals(logArea.getServerName())) {
                    ConsoleLog.resetTextArea(serverConfig.getServerName());
                }
            }
        });

        HBox hBox = new HBox();
        hBox.setPrefHeight(60d);
        titledPane.setGraphic(hBox);

        serverContainer.getChildren().add(titledPane);
    }

    public ListView<ServerUrl> drawUrlPanel(List<ServerUrl> serverUrls, ServerConfig serverConfig) {
        ObservableList<ServerUrl> urlList = FXCollections.observableArrayList(serverUrls);
        ListView<ServerUrl> serverListViews = new ListView<>(urlList);

        serverListViews.setCellFactory(new Callback<ListView<ServerUrl>, ListCell<ServerUrl>>() {
            @Override
            public ListCell<ServerUrl> call(ListView<ServerUrl> param) {
                ListCell<ServerUrl> listCell = new ListCell<ServerUrl>() {
                    @Override
                    protected void updateItem(ServerUrl item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            BorderPane urlPane = new BorderPane();

                            HBox labelBox = new HBox();
                            Label nameLabel = LabelFactory.getLabel(item.getUrlName());
                            nameLabel.setPrefWidth(100d);
                            Label urlLabel = LabelFactory.getLabel(item.getUrl());
                            labelBox.getChildren().addAll(nameLabel, urlLabel);
                            labelBox.setAlignment(Pos.CENTER_LEFT);

                            HBox btnBox = new HBox();
                            Button configButton = ButtonFactory.getButton("配置");
                            Button deleteButton = ButtonFactory.getButton("删除");
                            btnBox.setSpacing(15d);
                            btnBox.getChildren().addAll(configButton, deleteButton);
                            HBox.setMargin(deleteButton, new Insets(0, 20, 0, 0));

                            deleteButton.setOnAction(new DeleteUrlHandler(item, serverConfig, serverListViews, HomePage.this));
                            configButton.setOnAction(new UpdateUrlHandler(item, serverListViews, primaryStage));

                            urlPane.setLeft(labelBox);
                            urlPane.setRight(btnBox);
                            this.setGraphic(urlPane);

                        }
                    }
                };
                return listCell;
            }
        });

        serverListViews.prefHeightProperty().bind(Bindings.size(urlList).multiply(60));
        serverListViews.setFocusTraversable(false);

        return serverListViews;
    }

    public void refreshServerContainer() {
        serverContainer.getChildren().removeAll(serverContainer.getChildren());
        List<ServerConfig> serverConfigs = configuration.getServerConfigs();
        if (serverConfigs == null || serverConfigs.size() == 0) {
            serverConfigs = new ArrayList<>();
            configuration.setServerConfigs(serverConfigs);
        }

        for (ServerConfig serverConfig : serverConfigs) {
            drawServerPanel(serverContainer, serverConfig, primaryStage);
        }
    }

    public void restart() {
        cleanUp();
        start();
    }

    public void cleanUp() {
        for (String name : threadMap.keySet()) {
            threadMap.get(name).stopServer();
        }
    }

    public void replaceConfig(Configuration configuration) {
        this.configuration = configuration;
    }

    public Image getIcon() {
        return new Image("resources/panda.png");
    }

    public Background getBackGround() {
        BackgroundFill backgroundFill = new BackgroundFill(Color.GRAY, new CornerRadii(1),
                new Insets(0.0, 0.0, 0.0, 0.0));
        return new Background(backgroundFill);
    }
}
