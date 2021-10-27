package com.dayrain.handle;

import com.dayrain.entity.ConfigHolder;
import com.dayrain.entity.ServerConfig;
import com.dayrain.entity.ServerUrl;
import com.dayrain.server.ServerThread;
import com.dayrain.utils.ListViewHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;

public class UpdateUrlHandler implements EventHandler<ActionEvent> {

    private final ListView<ServerUrl> serverUrlListView;

    private final ServerUrl serverUrl;

    private final Stage primaryStage;

    public UpdateUrlHandler(ServerUrl serverUrl, ListView<ServerUrl> serverUrlListView, Stage primaryStage) {
        this.serverUrl = serverUrl;
        this.serverUrlListView = serverUrlListView;
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(ActionEvent event) {
        Stage stage = new Stage();
        Label nameLabel = new Label("接口名称:");
        nameLabel.setPrefWidth(80);
        TextField nameField = new TextField(serverUrl.getUrlName());
        Label urlLabel = new Label("接口地址:");
        TextField urlField = new TextField(serverUrl.getUrl());
        urlField.setPrefWidth(80);

        Label respLabel = new Label("返回结果:");
        TextArea textArea = new TextArea(serverUrl.getResponseBody());
        textArea.setPrefWidth(80);

        HBox btnHBox = new HBox();
        Label saveTips = new Label("重启后生效");
        saveTips.setStyle("-fx-background-color: #ff0000");
        Button saveButton = new Button("保存");
        btnHBox.getChildren().addAll(saveTips, saveButton);
        btnHBox.setAlignment(Pos.CENTER_RIGHT);
        btnHBox.setSpacing(20d);

        GridPane gridPane = new GridPane();
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(urlLabel, 0, 1);
        gridPane.add(urlField, 1, 1);
        gridPane.add(respLabel, 0, 2);
        gridPane.add(textArea, 1, 2);
        gridPane.add(btnHBox, 1, 3);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20d);
        gridPane.setVgap(10d);

        stage.setWidth(500);
        stage.setHeight(400);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(gridPane));
        stage.show();

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameField.getText();
                String url = urlField.getText();
                String resp = textArea.getText();
                serverUrl.setUrlName(name);
                serverUrl.setUrl(url);
                serverUrl.setResponseBody(resp);

                ListViewHelper.refresh(serverUrlListView);
                ConfigHolder.save();
                stage.close();
            }
        });
    }
}
