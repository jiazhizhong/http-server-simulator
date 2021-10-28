package com.dayrain.handle;

import com.dayrain.component.ConfigHolder;
import com.dayrain.component.RequestType;
import com.dayrain.component.ServerUrl;
import com.dayrain.style.ButtonFactory;
import com.dayrain.style.LabelFactory;
import com.dayrain.utils.ListViewHelper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


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
        Label nameLabel = LabelFactory.getLabel("接口名称:");
        nameLabel.setPrefWidth(80);
        TextField nameField = new TextField(serverUrl.getUrlName());
        Label urlLabel = LabelFactory.getLabel("接口地址:");
        TextField urlField = new TextField(serverUrl.getUrl());
        urlField.setEditable(false);
        urlField.setPrefWidth(80);

        Label methodLabel = LabelFactory.getLabel("请求方式:");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(FXCollections.observableArrayList("POST", "GET"));
        choiceBox.setValue("POST");
        urlField.setPrefWidth(80);

        Label respLabel = LabelFactory.getLabel("返回结果:");
        TextArea textArea = new TextArea(serverUrl.getResponseBody());
        textArea.setPrefWidth(80);

        HBox btnHBox = new HBox();

        Button saveButton = ButtonFactory.getButton("保存");
        btnHBox.getChildren().addAll(saveButton);
        btnHBox.setAlignment(Pos.CENTER_RIGHT);
        btnHBox.setSpacing(20d);

        GridPane gridPane = new GridPane();
        gridPane.add(methodLabel, 0, 0);
        gridPane.add(choiceBox, 1, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(urlLabel, 0, 2);
        gridPane.add(urlField, 1, 2);
        gridPane.add(respLabel, 0, 3);
        gridPane.add(textArea, 1, 3);
        gridPane.add(btnHBox, 1, 4);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20d);
        gridPane.setVgap(10d);

        stage.setWidth(550);
        stage.setHeight(450);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(gridPane));
        stage.getIcons().addAll(primaryStage.getIcons());
        stage.show();

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameField.getText();
                String url = urlField.getText();
                String resp = textArea.getText();
                String type = choiceBox.getValue();
                serverUrl.setUrlName(name);
                serverUrl.setUrl(url);
                serverUrl.setResponseBody(resp);
                serverUrl.setRequestType(type.equals(RequestType.POST.name()) ? RequestType.POST : RequestType.GET);

                ListViewHelper.refresh(serverUrlListView);
                ConfigHolder.save();
                stage.close();
            }
        });
    }
}
