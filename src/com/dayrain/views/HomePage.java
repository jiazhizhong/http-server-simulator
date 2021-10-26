package com.dayrain.views;

import com.dayrain.entity.Configuration;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {
    private Stage primaryStage;
    private Configuration configuration;

    public HomePage(Stage primaryStage, Configuration configuration) {
        this.primaryStage = primaryStage;
        this.configuration = configuration;
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


        //服务列表
        VBox serverBox = new VBox();

        HBox urlBox = new HBox();
        Label nameLabel = new Label("登录");
        Label urlLabel = new Label("/wms/login");
        Button configButton = new Button("配置");
        Button deleteButton = new Button("删除");
        urlBox.setSpacing(20d);
        urlBox.setAlignment(Pos.CENTER_LEFT);
        urlBox.getChildren().addAll(nameLabel, urlLabel, configButton, deleteButton);

        TitledPane titledPane = new TitledPane("众华WMS", urlBox);
        titledPane.setPrefWidth(600d);
        serverBox.getChildren().addAll(titledPane);

        borderPane.setLeft(serverBox);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setWidth(configuration.getWidth());
        primaryStage.setHeight(configuration.getHeight());
        primaryStage.show();
    }
}
