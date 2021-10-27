package com.dayrain.views;

import com.dayrain.entity.ConfigHolder;
import com.dayrain.entity.Configuration;
import com.dayrain.entity.Server;
import com.dayrain.entity.ServerConfig;
import com.dayrain.entity.ServerUrl;
import com.dayrain.handle.AddUrlHandler;
import com.dayrain.handle.DeleteUrlHandler;
import com.dayrain.handle.StartServerHandler;
import com.dayrain.handle.UpdateServerConfigHandler;
import com.dayrain.handle.UpdateUrlHandler;
import com.dayrain.server.ServerThread;
import com.dayrain.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage {

    private Stage primaryStage;

    private final Configuration configuration = ConfigHolder.init();

    private HashMap<String, ServerThread> threadMap = new HashMap<>();

    private List<ListView<ServerUrl>> listViews = new ArrayList<>();

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

        Menu menu2 = new Menu("日志");
        Menu menu3 = new Menu("帮助");
        menuBar.getMenus().addAll(menu1, menu2,  menu3);
        borderPane.setTop(menuBar);

        //渲染服务列表
        VBox serverContainer = new VBox();

        List<ServerConfig> serverConfigs = configuration.getServerConfigs();

        for (ServerConfig serverConfig : serverConfigs) {
            drawServerPanel(serverContainer, serverConfig, primaryStage);
        }

        borderPane.setLeft(serverContainer);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setWidth(configuration.getWidth());
        primaryStage.setHeight(configuration.getHeight());
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                FileUtils.saveConfig(configuration);
            }
        });
    }

    public void drawServerPanel(VBox serverContainer, ServerConfig serverConfig, Stage primaryStage) {

        VBox vBox = new VBox();
        HBox headBox = new HBox();
        Button editButton = new Button("配置参数");
        Button openButton = new Button("开启服务");
        Button addButton = new Button("添加接口");
        Circle statusCircle = new Circle();
        statusCircle.setRadius(10);
        statusCircle.setFill(Color.RED);
        //设置服务启动与关闭
        openButton.setOnAction(new StartServerHandler(openButton, statusCircle, serverConfig, threadMap));
        editButton.setOnAction(new UpdateServerConfigHandler(serverConfig, primaryStage));
        headBox.getChildren().addAll(openButton, editButton, addButton, statusCircle);
        HBox.setMargin(statusCircle, new Insets(0,0,0,30));
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
        titledPane.setPrefWidth(600d);
        serverContainer.getChildren().add(titledPane);
    }

    public ListView<ServerUrl> drawUrlPanel(List<ServerUrl> serverUrls, ServerConfig serverConfig) {
        ObservableList<ServerUrl> urlList = FXCollections.observableArrayList(serverUrls);
        ListView<ServerUrl> serverListViews = new ListView<>(urlList);
        listViews.add(serverListViews);
        serverListViews.setCellFactory(new Callback<ListView<ServerUrl>, ListCell<ServerUrl>>() {
            @Override
            public ListCell<ServerUrl> call(ListView<ServerUrl> param) {
                ListCell<ServerUrl> listCell =new ListCell<ServerUrl>() {
                    @Override
                    protected void updateItem(ServerUrl item, boolean empty) {
                        super.updateItem(item, empty);

                        if(empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        }else {
                            BorderPane urlPane = new BorderPane();

                            HBox labelBox = new HBox();
                            Label nameLabel = new Label(item.getUrlName());
                            nameLabel.setPrefWidth(100d);
                            Label urlLabel = new Label(item.getUrl());
                            labelBox.getChildren().addAll(nameLabel, urlLabel);
                            labelBox.setAlignment(Pos.CENTER_LEFT);

                            HBox btnBox = new HBox();
                            Button configButton = new Button("配置");
                            Button deleteButton = new Button("删除");
                            btnBox.setSpacing(15d);
                            btnBox.getChildren().addAll(configButton, deleteButton);

                            deleteButton.setOnAction(new DeleteUrlHandler(item, serverConfig, serverListViews, threadMap));
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

        return serverListViews;
    }

}
