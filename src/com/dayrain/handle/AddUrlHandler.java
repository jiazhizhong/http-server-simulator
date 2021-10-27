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
import java.util.List;

/**
 * 添加路径
 * @author peng
 * @date 2021/10/27
 */
public class AddUrlHandler implements EventHandler<ActionEvent> {

    private final ServerConfig serverConfig;

    private final ListView<ServerUrl> serverUrlListView;

    private final HashMap<String, ServerThread> threadMap;

    private final Stage primaryStage;

    public AddUrlHandler(ServerConfig serverConfig, ListView<ServerUrl> serverUrlListView, HashMap<String, ServerThread> threadMap, Stage primaryStage) {
        this.serverConfig = serverConfig;
        this.serverUrlListView = serverUrlListView;
        this.threadMap = threadMap;
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(ActionEvent event) {
        Stage stage = new Stage();
        Label nameLabel = new Label("接口名称:");
        nameLabel.setPrefWidth(80);
        TextField nameField = new TextField();
        Label urlLabel = new Label("接口地址:");
        TextField urlField = new TextField();
        urlField.setPrefWidth(80);

        Label respLabel = new Label("返回结果:");
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(80);

        HBox btnHBox = new HBox();
        Button saveButton = new Button("保存");
        btnHBox.getChildren().add(saveButton);
        btnHBox.setAlignment(Pos.CENTER_RIGHT);

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
                List<ServerUrl> serverUrls = serverConfig.getServerUrls();
                for (ServerUrl serverUrl : serverUrls) {
                    if(serverUrl.getUrlName().equals(name)) {
                        return;
                    }
                }
                String url = urlField.getText();
                String resp = textArea.getText();

                ServerUrl serverUrl = new ServerUrl(name, url, null, resp);

                serverUrls.add(serverUrl);
                ServerThread serverThread = threadMap.getOrDefault(serverConfig.getServerName(), null);
                if(serverThread != null) {
                    serverThread.addContext(serverUrl);
                }
                ListViewHelper.addAndRefresh(serverUrl, serverUrlListView);
                ConfigHolder.save();
                stage.close();
            }
        });
    }

}
