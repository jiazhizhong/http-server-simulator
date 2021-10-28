package com.dayrain.handle;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.ServerConfig;
import com.dayrain.style.ButtonFactory;
import com.dayrain.style.LabelFactory;
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

public class UpdateServerConfigHandler implements EventHandler<ActionEvent> {

    private final ServerConfig serverConfig;

    private final Stage primaryStage;

    public UpdateServerConfigHandler(ServerConfig serverConfig, Stage primaryStage) {
        this.serverConfig = serverConfig;
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(ActionEvent event) {
        Stage stage = new Stage();
        Label serverName = LabelFactory.getLabel("服务名称:");
        serverName.setPrefWidth(80);
        TextField nameField = new TextField(serverConfig.getServerName());
        Label portLabel = LabelFactory.getLabel("端口:");
        TextField portField = new TextField(String.valueOf(serverConfig.getPort()));
        portField.setPrefWidth(80);

        HBox btnHBox = new HBox();
        Label saveTips = LabelFactory.getLabel("重启后生效");
        Button saveButton = ButtonFactory.getButton("保存");
        btnHBox.getChildren().addAll(saveTips, saveButton);
        btnHBox.setAlignment(Pos.CENTER_RIGHT);
        btnHBox.setSpacing(20d);

        GridPane gridPane = new GridPane();
        gridPane.add(serverName, 0, 0);
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
                int port = Integer.parseInt(portField.getText());
                serverConfig.setPort(port);
                serverConfig.setServerName(name);
                ConfigHolder.save();
                stage.close();
            }
        });
    }
}
