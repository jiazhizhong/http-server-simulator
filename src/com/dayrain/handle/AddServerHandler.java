package com.dayrain.handle;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.Configuration;
import com.dayrain.component.ServerConfig;
import com.dayrain.style.ButtonFactory;
import com.dayrain.style.LabelFactory;
import com.dayrain.views.HomePage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * 添加server
 * @author peng
 * @date 2021/10/28
 */
public class AddServerHandler implements EventHandler<ActionEvent> {

    private final Stage primaryStage;

    private final Configuration configuration;

    private final HomePage homePage;

    public AddServerHandler(Stage primaryStage, Configuration configuration, HomePage homePage) {
        this.primaryStage = primaryStage;
        this.configuration = configuration;
        this.homePage = homePage;
    }

    @Override
    public void handle(ActionEvent event) {
        Stage stage = new Stage();
        Label nameLabel = LabelFactory.getLabel("服务名称:");
        nameLabel.setPrefWidth(80);
        TextField nameField = new TextField();
        Label portLabel = LabelFactory.getLabel("端口号:");
        TextField portField = new TextField();
        portField.setPrefWidth(80);

        HBox btnHBox = new HBox();
        Button saveButton = ButtonFactory.getButton("保存");
        btnHBox.getChildren().add(saveButton);
        btnHBox.setAlignment(Pos.CENTER_RIGHT);

        GridPane gridPane = new GridPane();
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(portLabel, 0, 1);
        gridPane.add(portField, 1, 1);
        gridPane.add(btnHBox, 1, 3);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20d);
        gridPane.setVgap(10d);

        stage.setWidth(500);
        stage.setHeight(400);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(gridPane));
        stage.getIcons().addAll(primaryStage.getIcons());
        stage.show();

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameField.getText();
                String port = portField.getText();
                ServerConfig serverConfig = new ServerConfig(name, Integer.parseInt(port), new ArrayList<>());
                configuration.getServerConfigs().add(serverConfig);
                homePage.refreshServerContainer();
                ConfigHolder.save();
                stage.close();
            }
        });
    }
}
